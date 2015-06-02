import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Locale;
import java.io.*;

public class Finder {

    public String checkAgeRegExp(String userAgeString) {
        Pattern p = Pattern.compile("(\\d)");
        Matcher m = p.matcher(userAgeString);
        String resultAge;
        StringBuffer buf = new StringBuffer("");
        while (m.find()) {
            buf.append(m.group());
            }
        resultAge = buf.toString();
        if (resultAge.length() > 2) {
            resultAge = "";
            }
        return resultAge;
        }
    public String checkNameRegExp(String userNameString){
        Pattern p = Pattern.compile("^[А-Я][а-я]{1,20}\\s[А-Я][а-я]{1,20}$");
        Matcher m = p.matcher(userNameString);
        String resultName="";
        if(m.matches()) {
            Pattern ppro = Pattern.compile("^[А-Яа-я]{2,20}[ ][А-Яа-я]{2,20}$");
            Matcher mpro = ppro.matcher(userNameString);
            if(!mpro.matches()) {
                String aName="", bName="";
                Pattern pa = Pattern.compile("^([А-Яа-я][а-я]{2,20})");
                Matcher ma = pa.matcher(userNameString);
                while (ma.find()) {
                    aName += ma.group();
                    aName = aName.toLowerCase(Locale.getDefault());
                    aName = aName.substring(0, 1).toUpperCase(Locale.getDefault()) + aName.substring(1);
                    }
                String cutName;
                cutName=userNameString.substring(aName.length());
                Pattern pb = Pattern.compile("^([А-Я][а-я]{1,19}[А-Яа-я])$");
                Matcher mb = pb.matcher(cutName);
                while (mb.find()) {
                    bName += mb.group();
                    bName = bName.toLowerCase(Locale.getDefault());
                    bName = bName.substring(0, 1).toUpperCase(Locale.getDefault()) + bName.substring(1);
                    }
                if(!bName.equals(""))
                    {resultName=aName+" "+bName;}
                }
            else {
                Pattern p3 = Pattern.compile("([А-Яа-я]{3,21})");
                Matcher m3 = p3.matcher(userNameString);
                StringBuffer bufRes = new StringBuffer();

                while (m3.find()) {
                    StringBuffer bufPart = new StringBuffer();
                    String PartName = "";
                    bufPart.append(m3.group());
                    PartName=bufPart.toString();
                    PartName = PartName.toLowerCase(Locale.getDefault());
                    PartName = PartName.substring(0, 1).toUpperCase(Locale.getDefault()) + PartName.substring(1);
                    bufRes.append(PartName);
                    bufRes.append(" ");
                    }
                resultName = bufRes.toString();
                }
            }
        else {
            resultName=userNameString;
            }
        return resultName;
        }
    public String checkMailRegExp(String userMailString){
        Pattern p = Pattern.compile("[a-z\\d]*[@][a-z\\d]*[.][a-z]{2,3}");
        Matcher m = p.matcher(userMailString);
        String resultMail;
        String leftMail="";
        String rightBefPoi="";
        String rightAftPoi="";
        if(m.matches())
            {
            resultMail=userMailString;
            }
        else {
            Pattern pl = Pattern.compile("^([a-z\\d]*[@])");
            Matcher ml = pl.matcher(userMailString);
            if(ml.find())
                {
                leftMail+=ml.group();
                }

            Pattern prb = Pattern.compile("[@]([a-z\\d]*)[.]*[a-z\\d]{2,3}$");
            Matcher mrb = prb.matcher(userMailString);
            if(mrb.find())
                {
                rightBefPoi+=mrb.group(1);
                }

            Pattern pra = Pattern.compile("([.][a-z\\d]{2,3}$)");
            Matcher mra = pra.matcher(userMailString);
            if(mra.find())
                {
                rightAftPoi+=mra.group();
                }
            if(rightAftPoi.equals("") || rightBefPoi.equals("") || leftMail.equals(""))
                {resultMail="";}
            else
            {resultMail=leftMail+rightBefPoi+rightAftPoi;}
            }
        return resultMail;
        }
    public String checkPhoneRegExp(String userPhoneString){
        Pattern p = Pattern.compile("(\\d)");
        Matcher m = p.matcher(userPhoneString);
        String resultPhone="+";
        String AllPhone="";
        String curPhone;
        StringBuffer bufPhone = new StringBuffer();
        while(m.find()) {
            bufPhone.append(m.group());
        }
        AllPhone=bufPhone.toString();
        String firstPhone=AllPhone.substring(0,1);
        if(AllPhone.length() != 11 || (!firstPhone.equals("7") && !firstPhone.equals("8")))
            {resultPhone="";}
        else {
            resultPhone+=firstPhone;
            curPhone=AllPhone.substring(1,4);
            resultPhone+=" ("+curPhone+") ";
            curPhone=AllPhone.substring(4,7);
            resultPhone+=curPhone;
            curPhone=AllPhone.substring(7,9);
            resultPhone+="-"+curPhone;
            curPhone=AllPhone.substring(9,11);
            resultPhone+="-"+curPhone;
            }
        return resultPhone;
        }

    private void GetContents(File fileIn, File fileOut){
        InputStream inputStream;
        OutputStream outputStream;

        try {
            inputStream=new FileInputStream("input.txt");
            outputStream=new FileOutputStream("output.txt");
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream,"UTF8"));
            BufferedWriter out=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF8"));
            while(reader.ready()) {
                StringBuffer textBuffer=new StringBuffer();
                textBuffer.append(reader.readLine());
                StringTokenizer tokenizer=new StringTokenizer(textBuffer.toString(), "|");

                if (!tokenizer.hasMoreTokens()) continue;
                String str = tokenizer.nextToken();
                str= checkNameRegExp(str);
                out.write(str+" ");
                if (!tokenizer.hasMoreTokens()) continue;
                str = tokenizer.nextToken();
                out.write(checkAgeRegExp(str)+" ");
                if (!tokenizer.hasMoreTokens()) continue;
                str = tokenizer.nextToken();
                out.write(checkPhoneRegExp(str)+" ");
                if (!tokenizer.hasMoreTokens()) continue;
                str = tokenizer.nextToken();
                out.write(checkMailRegExp(str));
                out.write("\n");
            }
            out.close();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args){
        Finder a = new Finder();
        File input = new File("D:\\projects\\lab3_pp\\src\\input.txt");
        File output = new File("D:\\projects\\lab3_pp\\src\\out.txt");

        a.GetContents(input, output);
    }
}
