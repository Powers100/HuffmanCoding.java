public class HuffmanTreeNode implements Comparable<HuffmanTreeNode>{

   private int frequency; //char frequency
   private char key; //character
   private HuffmanTreeNode left;
   private HuffmanTreeNode right;

   public char getKey(){
      return key;
   }
   public int getFrequency(){
      return frequency;
   }
   public void setLeft(HuffmanTreeNode node){
      left=node;
   }
   public HuffmanTreeNode getLeft(){
      return left;
   }
   public HuffmanTreeNode getRight(){
      return right;
   }
   public void setRight(HuffmanTreeNode node){
      right=node;
   }
   public HuffmanTreeNode(char key, int frequency){
      this.frequency = frequency; 
      this.key = key;
      left=right=null; 
   }
   
   public HuffmanTreeNode(int frequency){//for tmp node
      this.frequency = frequency; 
      this.key = '\u0000'; //placeholder
      left=right=null;
   }

   public int compareTo(HuffmanTreeNode node){
      return frequency-node.frequency;
   }
   public String toString(){
      return (key=='\u0000'?' ':key) +":"+frequency;
   }
}
