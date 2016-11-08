package com.stg.emailpoller;

import com.stg.emailpoller.dto.UserPhotoDto;
import com.stg.emailpoller.model.Photo;
import com.stg.emailpoller.model.User;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Email class.
 *
 * Created by dqromney on 11/4/16.
 */
public class Email {

    private ReadConfig readConfig;

    public Email() {
        readConfig = new ReadConfig("config.properties");
    }

    /**
     * Read email.
     *
     * @param pEmailAddress the {@link String} GMail email address
     * @param pPassword the {@link String} password for email address
     * @throws IOException when problem with I/O of call
     */
    public List<UserPhotoDto> read(String pEmailAddress, String pPassword) throws IOException {

        Properties configProps = readConfig.getPropValues();
        final String protocol = configProps.getProperty("mail.store.protocol");

        Properties props = new Properties();
        props.setProperty("mail.store.protocol", protocol);
        List<UserPhotoDto> userPhotoDtoList = new ArrayList<UserPhotoDto>(0);
        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect(AppConfig.GMAIL_IMAP_SERVICE_URL.getValue(), pEmailAddress, pPassword);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            System.out.println("Total Messages: " + inbox.getMessageCount());
            System.out.println("New Messages: " + inbox.getNewMessageCount());
            System.out.println("Unread Messages: " + inbox.getUnreadMessageCount());

            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message[] messages = inbox.search(ft);
            for (Message item : messages) {
                User user = new User();
                UserPhotoDto userPhotoDto = new UserPhotoDto();
                Address[] in = item.getFrom();
                for (Address address : in) {
                    extractNameEmail(address.toString(), user);
                }
                Multipart mp = (Multipart) item.getContent();
                if (mp != null) {
                    System.out.printf(String.format("contentType: %1$s", mp.getContentType()));
                }
                BodyPart bp = mp.getBodyPart(0);
                Photo photo = new Photo(item.getSubject(), bp.getContent().toString().substring(0,100), AppConfig.TEMP_IMAGE_URL.getValue(), item.getSentDate());
                userPhotoDto.setUser(user);
                userPhotoDto.setPhoto(photo);
                userPhotoDtoList.add(userPhotoDto);
                // System.out.println(userPhotoDto);
            }
        } catch (Exception mex) {
            mex.printStackTrace();
        }
        return userPhotoDtoList;
    }

    /**
     * Extract name and email from address.
     * @param pFrom the {@link String} from email string
     * @param user the {@link User} object to update
     * @return the {@link User} object
     */
    private User extractNameEmail(String pFrom, User user) {
        String fromString = pFrom.substring(6).trim(); // filter FROM:
        // Use DOTALL pattern
        Pattern p = Pattern.compile("(.*?)<([^>]+)>\\s*,?", Pattern.DOTALL);

        Matcher m = p.matcher(fromString);

        while (m.find()) {
            // filter newline
            String name = m.group(1).replaceAll("[\\n\\r]+", "");
            String email = m.group(2).replaceAll("[\\n\\r]+", "");
            user.setName(name);
            user.setEmail(email);
            // System.out.println(name + " -> " + email);
        }
        return user;
    }
}
