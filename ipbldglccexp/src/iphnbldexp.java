import java.io.File;
import java.io.IOException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

// External library. It must be include -classpath or project. 
// jexcelapi_2_6_12.zip 
// If using SE JDK greather thab version JDK 1.5 add lib  Properties ->  add Library -> New 
// Because it must be compailed JDK and should have source Dir 
import jxl.Workbook;

import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


public class iphnbldexp {

    private static Vector bldData;
   
   
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
   /* Turkish Unicode Charecters
    ð - \u011f
    Ð - \u011e
    ý - \u0131
    Ý - \u0130
    ö - \u00f6
    Ö - \u00d6
    ü - \u00fc
    Ü - \u00dc
    þ - \u015f
    Þ - \u015e
    ç - \u00e7
    Ç - \u00c7
    */
    
    private static String attrb_sourceName = "Sa\u011fl\u0131k";
    private static String tag_record = "Record";
    private static String attrb_type = "HKQuantityTypeIdentifierBloodGlucose";

    /**
     * Creates output.xls file
     * Required jxl api library
     * last download url http://sourceforge.net/projects/jexcelapi/files/
     * 
     * @param pdata A Vector Array that contains String Array of BloodGlucose date and value
     */
    public static void toExcel (Vector pdata, String outfile) {
        
        WritableWorkbook workbook;

        try {
            workbook = Workbook.createWorkbook(new File(outfile));
            WritableSheet sheet = workbook.createSheet("First Sheet", 0);
            Label label = new Label(0, 0, "Tarih"); 
            sheet.addCell(label); 
            Label label2 = new Label(1, 0, "mg/dL"); 
            sheet.addCell(label2); 
            int row = 0;
            Enumeration epdata = pdata.elements();
            while(epdata.hasMoreElements()){
                row++;
                String aaa[] = (String[])epdata.nextElement();
                //System.out.println(aaa[0]+","+aaa[1]);
                   
                
                SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
                Date dt = null;
                try {
                    dt = df.parse(aaa[0]);
                } catch (ParseException e) {
                    
                    e.printStackTrace();
                }
                WritableCellFormat dateFormat = new WritableCellFormat ();
                DateTime dateCell = new DateTime(0,row, dt, dateFormat);
                
                sheet.addCell(dateCell);
                try {
                Number numberd = new Number(1, row, Float.parseFloat(aaa[1]));
                    sheet.addCell(numberd);
                } catch (NumberFormatException e){ 
                    aaa[1]="0";
                    Number numberd = new Number(1, row, Float.parseFloat(aaa[1])); 
                sheet.addCell(numberd);}
                
            }
            
            workbook.write(); 
            workbook.close();
            System.out.println(row+" rows recorded...");
            
        } catch (IOException e) {
           
            e.printStackTrace();
        } catch (WriteException e) {
            
            e.printStackTrace();
        }
    }

    /**
     * Date parser: It converts YYYYMMDDhhmmss+ZZZZ value to DD.MM.YYYY hh:mm
     * @param trh String
     * @return trh4ex String
     */
    public static String tarihParser (String trh){
        
        String trh4ex;
        String trhyil;
        String trhay;
        String trhgun;
        String trhsaat;
        String trhdakika;
        String trhsaniye;
        
        trhyil = trh.substring(0,4);
        trhay = trh.substring(5,7);
        trhgun = trh.substring(8,10);
        trhsaat = trh.substring(11,13);
        trhdakika=trh.substring(14,16);
        trhsaniye=trh.substring(17,19);
        
        trh4ex = trhgun + "-" + trhay + "-" + trhyil + " " + trhsaat + ":" + trhdakika + ":" + trhsaniye; 
        return trh4ex;
        
    }
  

    /**
     * Specific XML file parser that gets parameters of
     * BloodGlucose date and value of iPhone health program exported file
     * @param filexml
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    @SuppressWarnings("unchecked")
    public static Vector ReadXML(String filexml) throws SAXException, 
                                                        IOException, 
                                                        ParserConfigurationException {
        Vector cellData = new Vector();
    try  {
        
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder;
            docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc;
            doc = docBuilder.parse (new File(filexml));
            // normalize text representation
             doc.getDocumentElement ().normalize ();
             System.out.println ("Root element : " + 
                  doc.getDocumentElement().getNodeName());

            NodeList nl = doc.getElementsByTagName(tag_record);
               Element e;
               Attr nsAttr;
               Attr nsAttr2;
               NamedNodeMap nnm;
               
               attrb atr  = new attrb();
               
               int i, len;
               int sw = 0; 
               
               len = nl.getLength();
                
                // loop for elements for tag name is "Record"
               for (int j=0; j < len; j++)
               {
                  e = (Element) nl.item(j);
                  //System.out.println(e.getTagName() + ":");
                  
             
                  nnm = e.getAttributes();
             
                  if (nnm != null)
                  {
                     int cnt = nnm.getLength();
  
                     for (i=0; i < cnt;  i++)
                     {
                        nsAttr = (Attr) nnm.item(i);
                        nsAttr2 = (Attr) nnm.item(2);
                         
                        String sourceNameN = nsAttr2.getName();
                        String sourceNameV = nsAttr2.getValue();
                         
                        atr.atrname = nsAttr.getName();
                        atr.atrval = nsAttr.getNodeValue();
                      
                        int ss = atr.atrval.length();
                        int tt =  sourceNameV.length();
                            
                        //Attribute of type="HKQuantityTypeIdentifierBloodGlucose" 
                        if (atr.atrname == "type" && ss == 36 && sourceNameN=="sourceName" && tt == 6){ 
                             String parseddata[] = new String[cnt];                        
                            //System.out.println(j+" "+cnt+" "+ss+" "  + atr.atrname + " = " + atr.atrval);
                             
                             for (int k=0; k<cnt; k++ ){
                             
                                 nsAttr = (Attr) nnm.item(k);
                                 atr.atrname = nsAttr.getName();
                                 atr.atrval = nsAttr.getNodeValue();
  
                               // System.out.println(k+" "  + atr.atrname + " = " + atr.atrval);
                                 
                                 /* This switch code for
                                  * Apple health export file, After 18-09-2015  6th. attribute sourceVersion="9.0 inserted 
                                  * old 6th. attribute escalete to 7th.  
                                  * */
                                 if (atr.atrname=="creationDate"){
                                    DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z" );
                                    Date dt  = dtf.parse(atr.atrval);
                                    Date dts = dtf.parse("2015-09-18 21:59:00 +0200");
                                    if (dt.compareTo(dts) < 0) {
                                        //System.out.print("*");
                                        sw = 0;
                                    } else sw = 1;
                                    
                                 }
                                 
                                 switch (sw){
                                 case 0 : 
                                     if  (k==0) {
                                        String ex4trh = tarihParser(atr.atrval);
                                        parseddata [0] = ex4trh;
                                    }
                                    if    (k==6) 
                                        parseddata [1] = atr.atrval;
                                     break;
                                 
                                 case 1:
                                     if  (k==0) {
                                         String ex4trh = tarihParser(atr.atrval);
                                         parseddata [0] = ex4trh;
                                     }
                                     if    (k==7) 
                                         parseddata [1] = atr.atrval;     
                                     break;
                                 }
                             }
                             
                             //System.out.println(parseddata[0]+parseddata[1]);
                             cellData.add(parseddata);
                                 
                         }
                              
                     }
                    
                  }
               }
            
        }
        catch (SAXParseException err) {
            System.out.println ("** Parsing error" + ", line " 
             + err.getLineNumber () + ", uri " + err.getSystemId ());
            System.out.println(" " + err.getMessage ());
        }
        catch (SAXException e) {
        Exception x = e.getException ();
        ((x == null) ? e : x).printStackTrace ();
        }catch (Throwable t) {
        t.printStackTrace ();
        }
       return cellData;
    } 




    public static void main(String[] args) {
        Date dt = new Date();
 
        int j = 0;
               
        System.out.println(dt.toString()+" AME(C), args["+args.length+"]");
        System.out.println("Extracts blood glucose values and dates from iPhone health application exported file (xml) to (xls) file...");
        
        if (args.length < 2)
        {
          System.out.println("Wrong Usage...");
          System.out.println("java -classpath [class_paths] iphnbldexp [input_dir]/input_file.xml [output_dir]/output_file.xls");
          System.exit(-1);
        }    
        
        for ( j=0; j<args.length; j++){
      
        if (j==0) System.out.println("Input file :"+args[j]+" ");
        if (j==1) System.out.println("Output file :"+args[j]+" ");
        }
        try {
            System.out.println("File :"+args[0]+ "\n <"+tag_record +" type=\""+attrb_type+"\" sourceName=\"" + attrb_sourceName + "\"... /> \nReading...");
            bldData=ReadXML(args[0]);
            System.out.println(args[1] + " Writing...");
            toExcel(bldData,args[1]);
        } catch (SAXException e) {
            
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            
            e.printStackTrace();
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        
    }
}
