package africa.springCore.delichopsbackend.common.utils;


import africa.springCore.delichopsbackend.infrastructure.exception.DeliChopsException;

import java.io.*;
import java.util.stream.Collectors;

import static africa.springCore.delichopsbackend.common.Message.FAILED_TO_GET_ACTIVATION_LINK;


public class HtmlFileUtility {
    public static String getFileTemplate(String filePath) throws DeliChopsException {
        try(BufferedReader reader =
                    new BufferedReader(new FileReader(filePath))){
            return  reader.lines().collect(Collectors.joining());
        }catch (IOException exception){
            throw new DeliChopsException(FAILED_TO_GET_ACTIVATION_LINK);
        }
    }

    public static String getFileTemplateFromClasspath(String resourcePath) throws DeliChopsException {
        try (InputStream inputStream = HtmlFileUtility.class.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new DeliChopsException("Resource not found: " + resourcePath);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                return reader.lines().collect(Collectors.joining());
            }
        } catch (IOException | DeliChopsException exception) {
            throw new DeliChopsException("Failed to read resource: " + resourcePath + " - " + exception.getMessage());
        }
    }
}
