import java.util.Scanner;

public class MainClass {
    public static void main(String[] args){
    
        System.out.print("Please define the corpus Location: ");
        Scanner sc = new Scanner(System.in);
        String DirName = sc.nextLine();

        CorpusReader corpusReader = new DirIteratorCorpusReader(DirName);

        while(corpusReader.hasDocument()){
            String docContent = corpusReader.processDocument();
            System.out.println(docContent);
            break;
        }

    }
}
