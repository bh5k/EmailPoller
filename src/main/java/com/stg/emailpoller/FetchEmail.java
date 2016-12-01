package com.stg.emailpoller;

import com.stg.emailpoller.dto.UserPhotoDto;
import com.stg.emailpoller.model.Photo;
import com.stg.emailpoller.model.User;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Fetch Email.
 * <p>
 * Created by dqromney on 11/15/16.
 */
public class FetchEmail {

    public static List<UserPhotoDto> fetch(String pop3Host, String storeType, String user, String password) {

        List<UserPhotoDto> userPhotoDtoList = new ArrayList<>(0);

        try {
            // create properties field
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "pop3");
            properties.put("mail.pop3.host", pop3Host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);
            emailSession.setDebug(true);

            // create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");

            store.connect(pop3Host, user, password);

            // create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                System.out.println("---------------------------------");
                UserPhotoDto userPhotoDto = new UserPhotoDto(new User(), new Photo());
                userPhotoDto = writePart(message, userPhotoDto);
                userPhotoDtoList.add(userPhotoDto);
                String line = "YES";
//                try {
//                    line = reader.readLine();
//                } catch(IOException e) {
//                    e.printStackTrace();
//                }
                if ("YES".equals(line)) {
                   message.writeTo(System.out);
                } else if ("QUIT".equals(line)) {
                   break;
                }
            }

            // close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userPhotoDtoList;
    }
//       public static void main(String[] args) {
//
//          String host = "pop.gmail.com";// change accordingly
//          String mailStoreType = "pop3";
//          String username =
//             "abc@gmail.com";// change accordingly
//          String password = "*****";// change accordingly
//
//          //Call method fetch
//          fetch(host, mailStoreType, username, password);
//
//       }

    /*
    * This method checks for content-type
    * based on which, it processes and
    * fetches the content of the message
    */
    public static UserPhotoDto writePart(Part p, UserPhotoDto pUserPhotoDto) throws Exception {
//        UserPhotoDto userPhotoDto = new UserPhotoDto(new User(), new Photo());
        if (p instanceof Message) {
            //Call methos writeEnvelope
            pUserPhotoDto = writeEnvelope((Message) p);
        }

        System.out.println("----------------------------");
        System.out.println("CONTENT-TYPE: " + p.getContentType());

        //check if the content is plain text
        if (p.isMimeType("text/plain")) {
            System.out.println("This is plain text");
            System.out.println("---------------------------");
            System.out.println((String) p.getContent());
            pUserPhotoDto.getPhoto().setText((String) p.getContent());
        }
        //check if the content has attachment
        else if (p.isMimeType("multipart/*")) {
            System.out.println("This is a Multipart");
            System.out.println("---------------------------");
            Multipart mp = (Multipart) p.getContent();
            int count = mp.getCount();
            for (int i = 0; i < count; i++)
                pUserPhotoDto = writePart(mp.getBodyPart(i), pUserPhotoDto);
        }
        //check if the content is a nested message
        else if (p.isMimeType("message/rfc822")) {
            System.out.println("This is a Nested Message");
            System.out.println("---------------------------");
            pUserPhotoDto = writePart((Part) p.getContent(), pUserPhotoDto);
        }
        //check if the content is an inline image
        else if (p.isMimeType("image/jpeg")) {
            System.out.println("--------> image/jpeg");
            Object o = p.getContent();

            InputStream x = (InputStream) o;
            // Construct the required byte array
            byte[] bArray = new byte[x.available()];
            int i = 0;
            System.out.println("x.length = " + x.available());
            while ((x.available()) > 0) {
                int result = x.read(bArray);
                if (result == -1)
                    i = 0;
                break;
            }
            Random random = new Random(new Date().getTime());
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy-hhmmss.SSS");
            String fileName = String.format("./ImageFile-%s.%d.%s", sdf.format( new Date() ), random.nextInt(9), "jpg");
            FileOutputStream f2 = new FileOutputStream(fileName);
            pUserPhotoDto.getPhoto().setImageUrl(fileName);
            f2.write(bArray);
        } else if (p.getContentType().contains("image/")) {
            System.out.println("content type" + p.getContentType());
            File f = new File("image" + new Date().getTime() + ".jpg");
            DataOutputStream output = new DataOutputStream(
                    new BufferedOutputStream(new FileOutputStream(f)));
            com.sun.mail.util.BASE64DecoderStream test =
                    (com.sun.mail.util.BASE64DecoderStream) p
                            .getContent();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = test.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } else {
            Object o = p.getContent();
            if (o instanceof String) {
                System.out.println("This is a string");
                System.out.println("---------------------------");
                System.out.println((String) o);
            } else if (o instanceof InputStream) {
                System.out.println("This is just an input stream");
                System.out.println("---------------------------");
                InputStream is = (InputStream) o;
                is = (InputStream) o;
                int c;
                while ((c = is.read()) != -1) {
                    if (Character.isLetterOrDigit(c))
                        System.out.write(c);
                }
            } else {
                System.out.println("This is an unknown type");
                System.out.println("---------------------------");
                System.out.println(o.toString());
            }
        }

        return pUserPhotoDto;
    }

    /*
    * This method would print FROM,TO and SUBJECT of the message
    */
    public static UserPhotoDto writeEnvelope(Message m) throws Exception {
        UserPhotoDto userPhotoDto = new UserPhotoDto();
        User user = new User();
        Photo photo = new Photo();
        System.out.println("This is the message envelope");
        System.out.println("---------------------------");
        Address[] a;

        // FROM
        if ((a = m.getFrom()) != null) {
            for (int j = 0; j < a.length; j++) {
                System.out.println("FROM: " + a[j].toString());
                user = extractNameEmail(a[j].toString(), user);
            }
        }

        // TO
        if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
            for (int j = 0; j < a.length; j++)
                System.out.println("TO: " + a[j].toString());
        }

        // SUBJECT
        if (m.getSubject() != null) {
            System.out.println("SUBJECT: " + m.getSubject());
            photo = new Photo(m.getSubject(), null, null, null);
        }

        // SENT DATE
        if (m.getSentDate() != null) {
            System.out.println("SENT DATE: " + m.getSentDate());
            photo.setSentDate(m.getSentDate());
        }
        userPhotoDto.setUser(user);
        userPhotoDto.setPhoto(photo);

        return userPhotoDto;
    }

    /**
     * Extract name and email from address.
     * @param pFrom the {@link String} from email string
     * @param user the {@link User} object to update
     * @return the {@link User} object
     */
    private static User extractNameEmail(String pFrom, User user) {
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
