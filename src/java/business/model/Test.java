/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business.model;

/**
 *
 * @author Sergey
 */
public class Test {

    public static void main(String[] args) throws Exception {
        PlacePolis pp = new PlacePolis();
        pp.getStringAddressPolis("ДНІПР", 1);
        //for (String temp: pp.aResult2){ System.out.println(temp); }
        for (String temp : pp.aResult3) {
            System.out.println(temp);
        }

//////     String s = "";
//////     String s1 = "";
//////  
////// for (int i = 0; i <= pp.aResult.size()-1; i++) 
////// {
//////     String[] sArr = pp.aResult.get(i);    
////// 
//////    s = "";
//////    s1 = "";
//////          
//////     //for (String temp: aResult_PlaceRegionTree){ System.out.println(temp); }
//////     
//////     
//////  //    System.out.println( sArr[0]+" "+sArr[1]+" "+sArr[2]+" "+sArr[3]+"   "+sArr[4]+" "+sArr[5]+" "+sArr[6]+" "+sArr[7] ); 
//////  
//////   
//////   if ( (sArr[0] != null)
//////        &  (!"г.п".equals(sArr[0]) ) )
//////        {
//////            if (sArr[2] != null){ // если есть замена
//////               s = s+"  "+ sArr[2] +" "+ sArr[3]; // если есть замена
//////               } else
//////               s = s+"  "+ sArr[0] +" "+ sArr[1]; // если нет замены
//////        }
////// 
//////   if ( (sArr[4] != null)
//////        &  (!"г.п".equals(sArr[4])) )
//////        {
//////            if (sArr[6] != null){ // если есть замена
//////               s = s+"  "+ sArr[6] +" "+ sArr[7]; // если есть замена
//////               } else
//////               s = s+"  "+ sArr[4] +" "+ sArr[5]; // если нет замены
//////        }
////// 
//////   if ( (sArr[8] != null)
//////        &  (!"г.п".equals(sArr[8])) )
//////        {
//////            if (sArr[10] != null){ // если есть замена
//////               s = s+"  "+ sArr[10] +" "+ sArr[11]; // если есть замена
//////               } else
//////               s = s+"  "+ sArr[8] +" "+ sArr[9]; // если нет замены
//////        }
////// 
//////   
//////   if ( (sArr[12] != null)
//////        &  (!"г.п".equals(sArr[12])) )
//////        {
//////            if (sArr[14] != null){ // если есть замена
//////               s = s+"  "+ sArr[14] +" "+ sArr[15]; // если есть замена
//////               } else
//////               s = s+"  "+ sArr[12] +" "+ sArr[13]; // если нет замены
//////        }
////// 
//////   if ( (sArr[16] != null)
//////        &  (!"г.п".equals(sArr[16])) )
//////        {
//////            if (sArr[18] != null){ // если есть замена
//////               s = s+"  "+ sArr[18] +" "+ sArr[19]; // если есть замена
//////               } else
//////               s = s+"  "+ sArr[16] +" "+ sArr[17]; // если нет замены
//////        }
////// 
//////     if ( (sArr[20] != null)
//////        &  (!"г.п".equals(sArr[20])) )
//////        {
//////            if (sArr[22] != null){ // если есть замена
//////               s = s+"  "+ sArr[22] +" "+ sArr[23]; // если есть замена
//////               } else
//////               s = s+"  "+ sArr[20] +" "+ sArr[21]; // если нет замены
//////        }
////// 
//////     
//////       s = s+"  "+ sArr[24] +" "+ sArr[25]+" "+ sArr[26]; // если есть замена
//////     
////// 

        //if (s != ""){
//////     System.out.println(s); 
        // s = "";
        // s1 = "";
        // }



    }
}
