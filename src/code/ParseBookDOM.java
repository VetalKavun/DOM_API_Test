/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 *
 * @author vetal
 */
public class ParseBookDOM {
    /**
     * обрабатывает элемент <some_element></some_element>
     * @param node
     * @param indent отступ
     */
    private void dumpElement(Element node, String indent){
        System.out.println(indent + "Element: " + node.getTagName());
        //Обработка аттрибута элемента
        NamedNodeMap nm = node.getAttributes();
        for (int i = 0; i < nm.getLength(); i++) {
            printNode(nm.item(i), indent + "   ");
            
        }
    }
    
    /**
     * Обрабатывает аттрибут типа <...some_attribute="some_value">
     * @param node
     * @param indent отступ 
     */
    private void dumpAttributeNode(Attr node, String indent){
        System.out.println(indent + "Attribute " + node.getName() + "=\"" + node.getValue() + "\"");
    }

    /**
     * Обрабатывает текстовое содержимое 
     * элемента <>some_text</>
     * @param node
     * @param indent отступ 
     */
    private void dumpTextNode(Text node, String indent){
        System.out.println(indent + "TEXT: " + node.getData());
    }
    
    /**
     * Рекурсивная процедура обработки 
     * узлов XML-документа
     * @param node
     * @param indent отступ 
     */
    private void printNode(Node node, String indent) {
        //получение типа узла
        int type = node.getNodeType();
        switch(type){
            case Node.ATTRIBUTE_NODE:
                dumpAttributeNode((Attr)node, indent);
                break;
            case Node.ELEMENT_NODE:
                dumpElement((Element)node, indent);
                break;
            case Node.TEXT_NODE:
                dumpTextNode((Text)node, indent);
                break;
        }
        
        //рекурсивная обработка дочерних узлов
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            printNode(list.item(i), indent + "  ");
            
        }
    }
    
    /**
     * Объявление метода main
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        try{
            String fileName = "book.xml";
            //создаем экземпляр класса DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //Создаем элзеирляр класса DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Запускаем анализ входного файла
            Document doc = builder.parse(new File(fileName));
            //Выводим дерево DOM на экран
            new ParseBookDOM().printNode(doc, "");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
