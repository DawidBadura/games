import java.io.*;

public class Map {
    public static char[][] LEVEL1=new char[36][48];

    public Map(String pathFile) {
        File file = new File(pathFile);
        BufferedReader br = null;

        try {

            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);

            String line;
            int j=0;

            while( (line = br.readLine()) != null ) {
                for(int i=0;i<line.length();i++){
                LEVEL1[j][i]=line.charAt(i);}
                j++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + file.toString());
        } catch (IOException e) {
            System.out.println("Unable to read file: " + file.toString());
        }
        finally {
            try {
                if(br!=null)br.close();
            } catch (IOException e) {
                System.out.println("Unable to close file: " + file.toString());
            }

        }
    }

    public void saveMap(String pathFile, char[][] objectList){

        File file = new File(pathFile);

        try (BufferedWriter br = new BufferedWriter(new FileWriter(file))) {
            for(int i=0;i<objectList.length;i++){
            br.write(objectList[i]);
            br.newLine();}

        } catch (IOException e) {
            System.out.println("Unable to read file " + file.toString());
        }


    }

    public static char[][] getLEVEL1() {
        return LEVEL1;
    }

}



