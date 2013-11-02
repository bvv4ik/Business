package com.bw.converter;

import com.hexiong.jdbf.DBFReader;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import jxl.Sheet;
import jxl.Workbook;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Загрузка элемента данных
 *
 * @author Belyavtsev Vladimir Vladimirovich
 */
public class ItemDataStream {

    public ItemDataStream() {
    }
    private StringBuffer osReturn = new StringBuffer();
    private String sSpliter = ",";
    private InputStream oInputStream;
    private aSourceFormat oSourceFormat;

    public static enum aSourceType {

        file, site, data
    }

    public static enum aSourceFormat {

        xls, dbf, csv
    }

    public ItemDataStream _Source(InputStream oInputStream) {
        this.oInputStream = oInputStream;
        return this;
    }

    private ItemDataStream _SourceFormat(String sSourcePath) {
        if (sSourcePath.endsWith(".xls")) {
            oSourceFormat = aSourceFormat.xls;
        } else if (sSourcePath.endsWith(".dbf")) {
            oSourceFormat = aSourceFormat.dbf;
        } else {// if(sSourcePath.endsWith(".csv"))
            oSourceFormat = aSourceFormat.csv;
        }
        return this;
    }

    public ItemDataStream _Source(aSourceType oSourceType, String sSource) throws Exception {
        if (oSourceType == aSourceType.file) {
            oInputStream = new FileInputStream(sSource);
            _SourceFormat(sSource);
        } else if (oSourceType == aSourceType.site) {
            if (sSource.startsWith("ftp://") || sSource.startsWith("http://")) {
                oInputStream = ((new URL(sSource)).openConnection()).getInputStream();
                _SourceFormat(sSource);
            }
        } else if (oSourceType == aSourceType.data) {
            oInputStream = new ByteArrayInputStream(sSource.getBytes());
            _SourceFormat(sSource);
        }
        return this;
    }

    public HashMap mColumn(String saColumnName, int nRowMin, int nRowMax) throws Exception {
        HashMap m = new HashMap();
        String[] asColumnName = saColumnName.split(sSpliter);
        int nRow, nColumn, nRows, nColumns, nColumnMax = asColumnName.length;
        for (int n = 0; n < nColumnMax; n++) {
            m.put(asColumnName[n], new ArrayList<String>());
        }
        //BufferedReader oReader = new BufferedReader(new InputStreamReader(oInputStream));
        Workbook oXLS = oSourceFormat == aSourceFormat.xls ? Workbook.getWorkbook(oInputStream) : null;
        DBFReader oDBF = oSourceFormat == aSourceFormat.dbf ? new DBFReader(oInputStream) : null;
        if (oXLS != null) {
            int nDef = 0;
            for (int n = 0; n < oXLS.getNumberOfSheets(); n++) {
                if (oXLS.getSheet(n).getRows() > 0) {
                    nDef = n;
                    break;
                }
            }
            Sheet oSheet = oXLS.getSheet(nDef);
            nRows = oSheet.getRows();
            if (nRowMax > 0 && nRows > nRowMax) {
                nRows = nRowMax;
            }
            nColumns = oSheet.getColumns();
            /*if (nColumnMax > 0 && nColumns > nColumnMax) {
             nColumns = nColumnMax;
             }*/
            nColumnMax = nColumnMax > nColumns ? nColumnMax : nColumns;
            /*if (nColumnMax > 0 && nColumnMax > nColumns) {
             nColumns = nColumnMax;
             }*/
            for (nRow = nRowMin; nRow < nRows; nRow++) {
                //for (nColumn = 0; nColumn < nColumns; nColumn++) {
                for (nColumn = 0; nColumn < nColumnMax; nColumn++) {
                    String sCell = "";
                    if (nColumn < nColumns) {
                        sCell = oSheet.getCell(nColumn, nRow).getContents();
                    }
                    ((ArrayList<String>) m.get(asColumnName[nColumn])).add(sCell);
                }
                /*if (nColumnMax > 0 && nColumnMax > nColumns) {
                 for (nColumn = nColumns-1; nColumn <= nColumnMax; nColumn++) {
                 ((ArrayList<String>)m.get(asColumnName[nColumn])).add("");
                 }
                 }*/

            }
        } else if (oDBF != null) {
            Object[] oaRow = null;
            nColumns = oDBF.getFieldCount();
            /*if (nListKey < 0) {
             nListKey = 0;
             } else {
             if (nListKey > (nColumns - 1)) {
             nListKey = nColumns - 1;
             }
             }
             if (!"".equals(sListKey)) {
             for (nColumn = 0; nColumn < nColumns; nColumn++) {
             if (sListKey.equals(oDBF.getField(nColumn).getName())) {
             nListKey = nColumn;
             }
             }
             }
             for (; oDBF.hasNextRecord(); nRow++) {
             oaRow = oDBF.nextRecord();
             if (nRow >= nListRowMin) {
             nListRow++;
             if ("body".equals(sListPart)) {
             for (nColumn = 0; nColumn < nColumns; nColumn++) {
             addCol(nColumn, ("" + oaRow[nColumn]).trim());
             }
             endRow();
             } else {
             addItem(nListRow, nRow, "" + oaRow[nListKey]);
             }
             if (nListRows > 0) {
             if (nListRow >= nListRows) {
             break;
             }
             }
             }
             }*/
        } else {
            /*for (nRow = 0; null != (sRow = oReader.readLine()); nRow++) {
             String[] asColumn = sRow.split(sSpliter), asColumnHead = null;
             nListRow++;
             if ("body".equals(sListPart)) {
             addRow(sRow.trim());
             }
             else {
             for (nColumn = 0; nColumn < asColumn.length; nColumn++) {
             sCell = asColumn[nColumn];
             if (nListKey == nColumn) {
             addItem(nListRow, nRow, sCell);
             break;
             }
             }
             }
             if (nListRows > 0) {
             if (nListRow >= nListRows) {
             break;
             }
             }
             }*/
        }
        //sReturn = osReturn.toString();
        //sReturn = "head".equals(sListPart) ? "{\"n\":\"" + (nListRow < 0 ? "?" : "" + nListRow) + "\",\"a\":[" + sReturn + "]}" : "body".equals(sListPart) ? sReturn : "[" + sReturn + "]";//System.out.println("nListRow="+nListRow);
        //return sReturn;
        return m;
    }//_.printStackTrace();
}
