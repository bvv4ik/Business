/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
//import com.hexiong.jdbf.DBFReader;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
//import jxl.Sheet;
//import jxl.Workbook;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author Sergey
 */
public class LoadDB {
//
//                InputStream oInputStream;
//                String sPathFile = "    ";
//            
//                if ("file".equals(sSourceType)) {
//                    sPathFile = sSourceFile;
//               
//                    if ("ftp://".equals(sPathFile.substring(0, 6)) || "http://".equals(sPathFile.substring(0, 7))) {//
//                        oInputStream = ((new URL(sSourceFile)).openConnection()).getInputStream();//URL ur = new URL("ftp://user:password@127.0.0.1:/in/" + "text.txt");
//                    } else {
//                        oInputStream = new FileInputStream(sSourceFile);
//                    }//oSourceReader=new BufferedReader(new InputStreamReader(new FileInputStream(sSourceFile)));
//                } else if ("base".equals(sSourceType)) {
//                    String sO = "";
//                    oConnection = oConnection(sSourceBase);
//                    try {
//                        oRowset = oConnection.prepareStatement("SELECT TOP 1 o,s FROM " + sSourceTable + " WHERE s='" + sSourceName + "'").executeQuery();
//                        if (oRowset.next()) {
//                            sO = oRowset.getString(1);
//                            sPathFile = oRowset.getString(2).replaceAll("\\_", "");
//                        }//System.out.println("sO.length()="+sO.length()+",s="+s+",sO="+sO);
//                    } finally {
//                        closeConnection(sSourceBase, oConnection);
//                    }
//                    oInputStream = new ByteArrayInputStream(sO.getBytes("UTF-8"));
//                } else if ("http".equals(sSourceType)) {
//                    sPathFile = sSourcePage + sSourceParam;
//                    oInputStream = oStream(sSourceSite, oRequest(), sPathFile);
//                    //-oInputStream = new ByteArrayInputStream((oWeb.load(sPathFile)).getBytes());//oSourceReader=new BufferedReader(new StringReader(oWeb.load(sSourcePage+sSourceParam)));
//                } else if ("text".equals(sSourceType)) {
//                    oInputStream = new ByteArrayInputStream(sSourceText.getBytes());
//                } else {
//                    oInputStream = new ByteArrayInputStream("".getBytes());// oSourceReader=new BufferedReader(new StringReader(""));
//                }
//               
//                BufferedReader oReader = new BufferedReader(new InputStreamReader(oInputStream));
//             
//                if ("list".equals(sType)) {
//                    Workbook oXLS = ".xls".equals(sPathFile.substring(sPathFile.length() - 4)) ? Workbook.getWorkbook(oInputStream) : null;//InputStream oXLS=new BufferedInputStream(getClass().getResourceAsStream(dataXLS));
//                    DBFReader oDBF = ".dbf".equals(sPathFile.substring(sPathFile.length() - 4)) ? new DBFReader(oInputStream) : null;//oDBF=new DBFReader(new ByteArrayInputStream(sO.getBytes()));
//                    if (oXLS != null) {
//                        int nDef = 0;
//                        for (int n = 0; n < oXLS.getNumberOfSheets(); n++) {
//                            if (oXLS.getSheet(n).getRows() > 0) {
//                                nDef = n;
//                                break;
//                            }
//                        }
//                        Sheet oSheet = oXLS.getSheet(nDef);
//                        nRows = oSheet.getRows();
//                        nColumns = oSheet.getColumns();
//                        if (nListKey < 0) {
//                            nListKey = 0;
//                        } else if (nListKey > (nColumns - 1)) {
//                            nListKey = nColumns - 1;
//                        }//System.out.println("nColumns="+nColumns+",nRows="+nRows);
//                        if (!"".equals(sListKey)) {
//                            for (nColumn = 0; nColumn < nColumns; nColumn++) {
//                                if (sListKey.equals(oSheet.getCell(nColumn, 0).getContents())) {
//                                    nListKey = nColumn;
//                                }
//                            }
//                        }
//                        if ("head".equals(sListPart)) {
//                            nListRow = nRows;//System.out.println("nColumn="+nColumn+",nRow="+nRow);
//                            for (nColumn = 0; nColumn < nColumns; nColumn++) {
//                                addHead(nColumn, oSheet.getCell(nColumn, 0).getContents(), oSheet.getCell(nColumn, 1).getContents().trim());
//                            }//sCol=oSheet.getCell(nColumn,0).getContents();sCell=oSheet.getCell(nColumn,1).getContents().trim();//osReturn.append((nColumn>0?",":"")+"{\"nID\":\""+nColumn+"\",\"value\":\""+sCol+"\",\"sInfo\":\" ("+sCell+",...)\"}");
//                        } else {
//                            for (nRow = nListRowMin; nRow < nRows; nRow++) {
//                                nListRow++;
//                                if ("body".equals(sListPart)) {
//                                    for (nColumn = 0; nColumn < nColumns; nColumn++) {
//                                        addCol(nColumn, oSheet.getCell(nColumn, nRow).getContents().trim());
//                                    }
//                                    endRow();//osReturn.append("\r\n");//sCell=oSheet.getCell(nColumn,nRow).getContents().trim();//osReturn.append((nColumn>0?sSpliter:"")+sCell);//System.out.println("nColumn="+nColumn+",nRow="+nRow+",sCell="+sCell);
//                                } else {
//                                    addItem(nListRow, nRow, oSheet.getCell(nListKey, nRow).getContents().trim());//sCell=oSheet.getCell(nListKey,nRow).getContents().trim();//osReturn.append((nListRow>1?",":"")+"{\"nID\":\""+nRow+"\",\"value\":\""+sCell+"\"}");
//                                }
//                                if (nListRows > 0) {
//                                    if (nListRow >= nListRows) {
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                    } else if (oDBF != null) {
//                        Object[] oaRow = null;
//                        nColumns = oDBF.getFieldCount();
//                        if (nListKey < 0) {
//                            nListKey = 0;
//                        } else {
//                            if (nListKey > (nColumns - 1)) {
//                                nListKey = nColumns - 1;
//                            }
//                        }//System.out.println("nColumns="+nColumns);
//                        if (!"".equals(sListKey)) {
//                            for (nColumn = 0; nColumn < nColumns; nColumn++) {
//                                if (sListKey.equals(oDBF.getField(nColumn).getName())) {
//                                    nListKey = nColumn;
//                                }
//                            }
//                        }//nListRow=-1;
//                        if ("head".equals(sListPart)) {
//                            for (nListRow = 0; oDBF.hasNextRecord(); nListRow++) {
//                                if (nListRow < 1) {
//                                    oaRow = oDBF.nextRecord();
//                                } else {
//                                    oDBF.nextRecord();
//                                }
//                            }
//                            for (nColumn = 0; nColumn < nColumns; nColumn++) {
//                                addHead(nColumn, oDBF.getField(nColumn).getName(), oaRow.length >= (nColumn + 1) ? "" + oaRow[nColumn] : "");
//                            }//sCol=oDBF.getField(nColumn).getName();//sCell="";if(oaRow.length>=(nColumn+1))sCell=(String)oaRow[nColumn];//osReturn.append((nColumn>0?",":"")+"{\"nID\":\""+nColumn+"\",\"value\":\""+sCol+"\",\"sInfo\":\" ("+sCell+",...)\"}");
//                        } else {
//                            for (; oDBF.hasNextRecord(); nRow++) {
//                                oaRow = oDBF.nextRecord();
//                                if (nRow >= nListRowMin) {
//                                    nListRow++;//String[] saLine;//System.out.println("String[0]="+oaRow[0]);//saLine=(String[])oDBF.nextRecord();
//                                    if ("body".equals(sListPart)) {
//                                        for (nColumn = 0; nColumn < nColumns; nColumn++) {
//                                            addCol(nColumn, ("" + oaRow[nColumn]).trim());
//                                        }
//                                        endRow();//osReturn.append("\r\n");//sCell=((String)oaRow[nColumn]).trim();//osReturn.append((nColumn>0?sSpliter:"")+sCell);//System.out.println("nColumn="+nColumn+",nRow="+nRow+",sCell="+sCell);//saLine[nListKey]//sLine=(String)oaRow[nColumn];//System.out.println("sLine="+sLine);
//                                    } else {
//                                        addItem(nListRow, nRow, "" + oaRow[nListKey]);
//                                    }//sCell=(String)oaRow[nListKey];//osReturn.append((nListRow>1?",":"")+"{\"nID\":\""+nRow+"\",\"value\":\""+sCell+"\"}");
//                                    if (nListRows > 0) {
//                                        if (nListRow >= nListRows) {
//                                            break;
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    } else {
//                        for (nRow = 0; null != (sRow = oReader.readLine()); nRow++) {
//                            String[] asColumn = sRow.split(sSpliter), asColumnHead = null;
//                            if ("head".equals(sListPart)) {
//                                for (nListRow = 1; null != (sRow = oReader.readLine()); nListRow++) {
//                                    if (nListRow < 2) {
//                                        asColumnHead = sRow.split(sSpliter);
//                                    }
//                                }
//                                for (nColumn = 0; nColumn < asColumn.length; nColumn++) {
//                                    addHead(nColumn, asColumn[nColumn], asColumnHead != null ? nColumn < asColumnHead.length ? asColumnHead[nColumn] : "" : "");
//                                }
//                                break;//sCol=oS.nextToken();//sCell="";if(oSs!=null)if(oSs.hasMoreTokens())sCell=oSs.nextToken();//osReturn.append((nColumn>0?",":"")+"{\"nID\":\""+nColumn+"\",\"value\":\""+sCol+"\",\"sInfo\":\" ("+sCell+",...)\"}");
//                            } else if (nRow >= nListRowMin) {
//                                nListRow++;
//                                if ("body".equals(sListPart)) {
//                                    addRow(sRow.trim());
//                                }//osReturn.append(sRow.trim()+"\r\n");
//                                else {
//                                    //for (nColumn = 0; oS.hasMoreTokens(); nColumn++) {
//                                    for (nColumn = 0; nColumn < asColumn.length; nColumn++) {
//                                        //sCell = oS.nextToken();
//                                        sCell = asColumn[nColumn];
//                                        if (nListKey == nColumn) {
//                                            addItem(nListRow, nRow, sCell);
//                                            break;
//                                        }//osReturn.append((nListRow>1?",":"")+"{\"nID\":\""+nRow+"\",\"value\":\""+sCell+"\"}");
//                                    }
//                                }
//                                if (nListRows > 0) {
//                                    if (nListRow >= nListRows) {
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                    }//}catch(Exception _){sReturn=osReturn.toString();sReturn=sErr+"List:"+_.getMessage()+"!!!sReturn="+sReturn;System.out.println(sReturn);_.printStackTrace();throw new RuntimeException(_);}
//                } else {
//                    char ao[] = new char[1];
//                    while (oReader.read(ao) > 0) {
//                        osReturn.append(ao);
//                    }
//                }
//
//            sReturn = osReturn.toString();
//            sReturn = "head".equals(sListPart) ? "{\"n\":\"" + (nListRow < 0 ? "?" : "" + nListRow) + "\",\"a\":[" + sReturn + "]}" : "body".equals(sListPart) ? sReturn : "[" + sReturn + "]";
//            
//
////            [10:51:19] Belyavtsev Vladimir Vladimirovich (dn310780bvv): это тебе основные кусочки кода, которые позволят брать файл xls,dbf или просто текст с любого источника, включая FTP, HTTP или файловой системы...
////[10:51:39] Belyavtsev Vladimir Vladimirovich (dn310780bvv):     
////            
//            private void addHead(int nID, String sID, String s) {
//        osReturn.append((nID > 0 ? "," : "") + "{\"nID\":\"" + nID + "\",\"value\":\"" + sID + "\",\"sInfo\":\" (" + s + ",...)\"}");//*5//osReturn.append((nColumn>0?",":"")+"{\"nID\":\""+nColumn+"\",\"value\":\""+sCol+"\",\"sInfo\":\" ("+sCell+",...)\"}");
//    }
//
//    private void addItem(int nCount, int nID, String sID) {
//        osReturn.append((nCount > 1 ? "," : "") + "{\"nID\":\"" + nID + "\",\"value\":\"" + sID + "\"}");//*5//osReturn.append((nListRow>1?",":"")+"{\"nID\":\""+nRow+"\",\"value\":\""+sCell+"\"}");
//    }
//
//    private void addCol(int nColumn, String sID) {
//        osReturn.append((nColumn > 0 ? sSpliter : "") + sID);//*3//osReturn.append((nColumn>0?sSpliter:"")+sCell);
//        //_Rise("_____________________________sSpliter="+sSpliter+",sID="+sID);
//    }
//
//    private void addRow(String sRow) {
//        osReturn.append(sRow + "\r\n");//*1//osReturn.append(sCell+"\r\n");//*1//osReturn.append(sRow.trim()+"\r\n");
//    } 
//
//    private void endRow() {
//        osReturn.append("\r\n");//*3//osReturn.append("\r\n");
//    }
////[11:05:12] Belyavtsev Vladimir Vladimirovich (dn310780bvv): тут у меня даже есть вариант подгрузки файла из поля TEXT базы, так что, как говорится "полный набор"...
////но там и прилично мусора и не очень красивых конструкций, т.к. делал это я уже очень давно и небыло времени на оптимизацию и украшательство...
////ты-ж это все в отдельный класс запихни
////[11:05:49] Belyavtsev Vladimir Vladimirovich (dn310780bvv): и сразу избавься от комментов, чтоб было легче читать код
////    
//    
//    
}
