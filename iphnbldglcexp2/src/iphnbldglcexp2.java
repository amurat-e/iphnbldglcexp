
import java.io.File;
import java.io.IOException;

import java.util.Date;

import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class iphnbldglcexp2 {
 
    static int srcflg = 0;
    static int obsflg = 0;
 
 
    public static class attrb {
        private String atrname;
        private String atrval;
        public String getAtrname(){
            return atrname;
        }
        public String getAtrval(){
            return atrval;
        }
    }
 
    public static String tarihParser (String trh){
        
        String trh4ex;
        String trhyil;
        String trhay;
        String trhgun;
        String trhsaat;
        
        trhyil = trh.substring(0,4);
        trhay = trh.substring(4,6);
        trhgun = trh.substring(6,8);
        trhsaat = trh.substring(8,10);
        
        trh4ex = trhgun + "-" + trhay + "-" + trhyil + " " + trhsaat + ":00:00"; 
        return trh4ex;
        
    }    
    
    public static void ReadXML(String filexml) throws Exception {
        Vector cellData = new Vector();
    try  {
        
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File(filexml));
     
             //doc.getDocumentElement ().normalize ();
             System.out.println ("Root element of the doc is " + doc.getDocumentElement().getNodeName());
            if (doc.hasChildNodes()) {
            
                    printNote(doc.getChildNodes());
            
            }           

        }             
        catch (Exception e) {

        e.printStackTrace ();
        }

    }    
    
    private static void printNote(NodeList nl) {
       
 
        for (int count = 0; count < nl.getLength(); count++) {
           
           Node nd = nl.item(count);
           if (nd.getNodeType() == Node.ELEMENT_NODE ) {
           
           if (nd.getNodeName()=="observation" || obsflg == 1){
            
            if (nd.getNodeName()=="source" || srcflg == 1){
               srcflg = 1;
               if (srcflg==1 && nd.getNodeName()=="value" ){
               System.out.println("Node Name =" + nd.getNodeName().trim() + " [OPEN]" + "Node Value =" + nd.getTextContent().trim());
               srcflg = 0;
            }
            }
               if (nd.hasAttributes()&&(nd.getNodeName()=="low" || nd.getNodeName()=="high")) {
               
                   NamedNodeMap nodeMap = nd.getAttributes();
                   
                   for (int i = 0; i < nodeMap.getLength(); i++) {
                   
                           Node node = nodeMap.item(i);
                           System.out.print( nd.getNodeName().trim() + " : " + node.getNodeValue().trim()+"\n");
                          
                   
                   }           
               
               }
               obsflg = 1;
           } 
           else 
           obsflg = 0;
           
               if (nd.hasChildNodes()) {
               
                       // loop again if has child nodes
                       printNote(nd.getChildNodes());
               
               }
           }

               
               //System.out.println("Node Name =" + nd.getNodeName() + " [CLOSE]");        
        
           
        
        //  toExcel(cellData);
        }   
    
    }
    
 
    
    
    public static void main(String[] args) {
       // iphnbldglcexp2 iphnbldglcexp2 = new iphnbldglcexp2();
        Date dt = new Date();
        
        int j = 0;
               
        System.out.println(dt.toString()+" AME(C), args["+args.length+"]");
        System.out.println("iPhone Saglik program export.xml den output.xls cevirici...");
        
        if (args.length < 1)
        {
          System.out.println("Yanlis Kullanim Bagdat'tan Doner...");
          System.out.println("Soyleki : dosya_adi.xml");
          System.exit(-1);
        }    
        int b = 0;
        for ( j=0; j<args.length; j++){
        b=j+1;
        if (j<1) System.out.print("Dosya :"+args[j]+" ");
        }
        try {
            ReadXML(args[j-1]);
        } catch (Exception e) {
       
            e.printStackTrace();
        }  
        
        
        
    }
}
