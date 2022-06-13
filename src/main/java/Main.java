import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        writeString("json.json", listToJson(parseXML("data.xml")));

    }

    public static List<Employee> parseXML(String path) {
        List<Employee> list = new ArrayList<>();
        try {

            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(new File(path));

            Node root = document.getDocumentElement();
            NodeList nodeList = root.getChildNodes();

            for (int a = 0; a < nodeList.getLength(); a++) {

                Node node = nodeList.item(a);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Employee employee = new Employee();

                    NodeList childNodes = nodeList.item(a).getChildNodes();
                    for (int b = 0; b < childNodes.getLength(); b++) {
                        Node childNode = childNodes.item(b);

                        if (childNode.getNodeType() == Node.ELEMENT_NODE) {

                            switch (childNode.getNodeName()) {
                                case "id":
                                    employee.setId(Long.parseLong(childNode.getTextContent()));
                                    break;
                                case "firstName":
                                    employee.setFirstName(childNode.getTextContent());
                                    break;
                                case "lastName":
                                    employee.setLastName(childNode.getTextContent());
                                    break;
                                case "country":
                                    employee.setCountry(childNode.getTextContent());
                                    break;
                                case "age":
                                    employee.setAge(Integer.parseInt(childNode.getTextContent()));
                                    break;
                            }
                        }

                    }
                    list.add(employee);

                }

            }

        } catch (IOException | ParserConfigurationException | SAXException a) {
            a.printStackTrace();
        }
        return list;
    }

    public static <T> String listToJson(List<T> list) {
        Type listType = new TypeToken<List<T>>() {
        }.getType();

        return new Gson().toJson(list, listType);

    }

    public static void writeString(String path, String json) {
        try (FileWriter fileWriter = new FileWriter(path)) {
            fileWriter.write(json);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
