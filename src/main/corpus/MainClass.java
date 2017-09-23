import java.util.Scanner;

public class MainClass {
    public static void main(String[] args){

    System.out.print("Please define the corpus Location: ");
    Scanner sc = new Scanner(System.in);
    String DirName = sc.nextLine();

    DirIteratorCorpusReader dirReader = new DirIteratorCorpusReader(DirName);
    SimpleTokenizer st = new SimpleTokenizer();
    st.tokenize(String docContent);
    
    }
}
