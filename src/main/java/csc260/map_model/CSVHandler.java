package csc260.map_model;
import java.util.*;
import java.io.*;

public class CSVHandler implements FileHandler {

  private FileHandler successor;

  public CSVHandler(){
    successor = new TXTHandler();
  }

  public boolean isHandleable(String fileType){
    return fileType.equals(".csv");
  }

  public String fileToString(File file) {
    String fileName = file.getName();
    int index = fileName.lastIndexOf(".");
    String fileType = fileName.substring(index);

    if (isHandleable(fileType)){
      String contents = "";
      try {
          Scanner scanner = new Scanner(file);
          while (scanner.hasNextLine())contents+= scanner.nextLine() + "\n";
      } catch (FileNotFoundException exception) {
          System.out.println("Invalid file");
      }
      return contents;
    } else {
      return successor.fileToString(file);
    }
  }

  public double getWeight(String fileContents) {
      double weight = 0;
      Scanner scanner = new Scanner(fileContents);
      //scanner.nextLine();
      while (scanner.hasNextLine()) {
          String temp = scanner.nextLine();
          if (!scanner.hasNext()){
              String[] contents = temp.split(",");
              weight += Double.valueOf(contents[2]);
          }
      }
      return weight;
  }


  public Segment fileToSegment (File file){
    String fileName = file.getName();
    int indexOfLastDot = fileName.lastIndexOf(".");
    String fileType = fileName.substring(indexOfLastDot);

    if(isHandleable(fileType)){
      String start = "";
      String destination = "";
      String intermediatePath = "";
      String originalContent = fileToString(file);
      double segmentWeight = getWeight(originalContent);
      // String originalContent = fileToString(file);

      String temp = fileName.replace(".csv","");
      Scanner scan = new Scanner(temp);
      scan.useDelimiter("-");
      int index = 0;
      while (scan.hasNext()){
          if (index == 0){
              start = scan.next();
          } else if (index == 1){
              destination = scan.next();
          } else {
              intermediatePath += scan.next();
              if (scan.hasNext()) intermediatePath += ",";
          }
          index++;
      }

      // Set segment attributes
      Segment segment = new Segment();
      segment.setIsSelected(false);
      segment.setFilePath(file.getPath());
      segment.setFileName(temp);
      segment.setStart(start);
      segment.setDestination(destination);
      segment.setIntermediatePath(intermediatePath);
      segment.setWeight(segmentWeight);
      segment.setOriginalContent(originalContent);
      return segment;
    }
    else{
      return successor.fileToSegment(file);
    }
  }

  public void stringToExportFile (String routeInString, String fileOutputPath, String fileName, String fileType) throws Exception{
    if(isHandleable(fileType)){
      String outputPath = fileOutputPath + "/" + fileName + fileType;
      try {
          FileWriter writer = new FileWriter(outputPath);
          writer.write(routeInString);
          writer.flush();
          writer.close();
      } catch (Exception e) {
          System.out.println("Invalid output");
      }
    }
    else{
      successor.stringToExportFile(routeInString,fileOutputPath,fileName,fileType);
    }
  }

 //  // public void save (ArrayList<Segment> segmentBankContents, ArrayList<Segment> routeContents, String fileOutputPath, String fileName) {
 //      String patrick = "patrick";
 //      String toBuild = "";
 //
 //      toBuild += patrick;
 //      for (Segment segment : segmentBankContents) {
 //          toBuild += segment.segToSaveString();
 //      }
 //
 //      toBuild += patrick;
 //      for (Segment segment : routeContents) {
 //          toBuild += segment.segToSaveString();
 //      }
 //
 //      try {
 //          stringToExportFile(toBuild,fileOutputPath,fileName,".txt");
 //      } catch (Exception e) {
 //          e.printStackTrace();
 //      }
 //  }
 //
 //  public void load (File txtFile, ArrayList<Segment> segmentBankContents, ArrayList<Segment> routeContents) {
 //
 //    String txtContents = "";
 //    try {
 //        Scanner txtScanner = new Scanner(txtFile);
 //        while (txtScanner.hasNext()){
 //           txtContents += txtScanner.next();
 //        }
 //
 //        String[] routeSegBankSeparator = txtContents.split("patrick");
 //        String[] bankItems = routeSegBankSeparator[1].split("kevin");
 //        String[] routeItems = routeSegBankSeparator[2].split("kevin");
 //
 //        for (int x=1; x<bankItems.length; x++) {
 //           Segment temp = new Segment();
 //
 //           String[] segFields = bankItems[x].split("woody");
 //           temp.setOriginalContent(segFields[1]);
 //
 //           if (segFields[2].equals("true")) temp.setIsSelected(true);
 //           else temp.setIsSelected(false);
 //
 //           if (segFields[3].equals("true")) temp.setIsAdded(true);
 //           else temp.setIsAdded(false);
 //
 //           // temp.setNickName(segFields[4]);
 //           // temp.renameStart(segFields[5]);
 //           // temp.renameDestination(segFields[6]);
 //           // temp.setFileName(segFields[7]);
 //           // temp.setFilePath(segFields[8]);
 //           // temp.setStart(segFields[9]);
 //           // temp.setDestination(segFields[10]);
 //           // temp.setIntermediatePath(segFields[11]);
 //           // temp.setWeight(Double.valueOf(segFields[12]));
 //           // temp.setStartX(Integer.valueOf(segFields[13]));
 //           // temp.setStartY(Integer.valueOf(segFields[14]));
 //           // temp.setEndX(Integer.valueOf(segFields[15]));
 //           // temp.setEndY(Integer.valueOf(segFields[16]));
 //
 //           temp.setNickName(segFields[4]);
 //           temp.renameStart(segFields[5]);
 //           temp.renameDestination(segFields[6]);
 //           temp.setFileName(segFields[7]);
 //           temp.setFilePath(segFields[8]);
 //           temp.setStart(segFields[9]);
 //           temp.setDestination(segFields[10]);
 //           temp.setIntermediatePath(segFields[11]);
 //           temp.setStartX(Integer.valueOf(segFields[12]));
 //           temp.setStartY(Integer.valueOf(segFields[13]));
 //           temp.setEndX(Integer.valueOf(segFields[14]));
 //           temp.setEndY(Integer.valueOf(segFields[15]));
 //           temp.setWeight(Double.valueOf(segFields[16]));
 //
 //           segmentBankContents.add(temp);
 //       }
 //
 //       for (int x=1; x<routeItems.length; x++) {
 //           Segment temp = new Segment();
 //
 //           String[] segFields = routeItems[x].split("woody");
 //           temp.setOriginalContent(segFields[1]);
 //
 //           if (segFields[2].equals("true")) temp.setIsSelected(true);
 //           else temp.setIsSelected(false);
 //
 //           if (segFields[3].equals("true")) temp.setIsAdded(true);
 //           else temp.setIsAdded(false);
 //
 //           // temp.setNickName(segFields[4]);
 //           // temp.renameStart(segFields[5]);
 //           // temp.renameDestination(segFields[6]);
 //           // temp.setFileName(segFields[7]);
 //           // temp.setFilePath(segFields[8]);
 //           // temp.setStart(segFields[9]);
 //           // temp.setDestination(segFields[10]);
 //           // temp.setIntermediatePath(segFields[11]);
 //           // temp.setWeight(Double.valueOf(segFields[12]));
 //           // temp.setStartX(Integer.valueOf(segFields[13]));
 //           // temp.setStartY(Integer.valueOf(segFields[14]));
 //           // temp.setEndX(Integer.valueOf(segFields[15]));
 //           // temp.setEndY(Integer.valueOf(segFields[16]));
 //
           // temp.setNickName(segFields[4]);
           // temp.renameStart(segFields[5]);
           // temp.renameDestination(segFields[6]);
           // temp.setFileName(segFields[7]);
           // temp.setFilePath(segFields[8]);
           // temp.setStart(segFields[9]);
           // temp.setDestination(segFields[10]);
           // temp.setIntermediatePath(segFields[11]);
           // temp.setStartX(Integer.valueOf(segFields[12]));
           // temp.setStartY(Integer.valueOf(segFields[13]));
           // temp.setEndX(Integer.valueOf(segFields[14]));
           // temp.setEndY(Integer.valueOf(segFields[15]));
           // temp.setWeight(Double.valueOf(segFields[16]));
 //
 //           routeContents.add(temp);
 //       }
 //
 //         // SegmentBank loadedSegmentBank = new SegmentBank();
 //         // loadedSegmentBank.setSegmentBankContents(segmentBankContents);
 //
 //     } catch (FileNotFoundException e) {
 //         System.out.println("FileNotFoundException is thrown");
 //     }
 // }
}
