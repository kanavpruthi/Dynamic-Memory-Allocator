// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.
public class A2DynamicMem extends A1DynamicMem {
    public A2DynamicMem() {  super(); }
    public A2DynamicMem(int size) { super(size); }
    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }
    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 
    // Your BST (and AVL tree) implementations should obey the property that keys in the left subtree <= root.key < keys in the right subtree. How is this total order between blocks defined? It shouldn't be a problem when using key=address since those are unique (this is an important invariant for the entire assignment123 module). When using key=size, use address to break ties i.e. if there are multiple blocks of the same size, order them by address. Now think outside the scope of the allocation problem and think of handling tiebreaking in blocks, in case key is neither of the two. 
    
    public int Allocate(int blockSize) {
        if(blockSize<=0)
            return -1;
        Dictionary allocMem=this.freeBlk.Find(blockSize,false);
        if(allocMem==null)
            return -1;    
        else if(allocMem.size==blockSize){
            this.allocBlk.Insert(allocMem.address,allocMem.size,allocMem.address);
            this.freeBlk.Delete(allocMem);
            return allocMem.address;
        }
        else{
            int tot=allocMem.size;
            Dictionary allocFin = new A1List(allocMem.address,blockSize,allocMem.address);
            this.allocBlk.Insert(allocMem.address,blockSize,allocMem.address);
            this.freeBlk.Insert(allocMem.address+blockSize,tot-blockSize,tot-blockSize);
            this.freeBlk.Delete(allocMem);
            return allocFin.address;
        }
        
    } 
    
    public int Free(int startAddr){
         Dictionary freeMem= this.allocBlk.Find(startAddr,true);
         if(freeMem==null)
             return -1;
         else{
             this.freeBlk.Insert(freeMem.address,freeMem.size,freeMem.size);
             this.allocBlk.Delete(freeMem);  
             return 0;
         }
    }
    
    public void Defragment(){
        AVLTree tree=new AVLTree();
        Dictionary node=freeBlk.getFirst();
        if(node==null || node.getNext()==null) return;
        while(node!=null){
            tree.Insert(node.address,node.size,node.address);
            node=node.getNext();
        }
        AVLTree current = tree.getFirst();
        if(current==null)
            return;
        while(current.getNext()!=null){
            if((current.address+current.size)==current.getNext().address){
                AVLTree q = current.getNext();
                Dictionary d1=new AVLTree(current.address,current.size,current.size);
                Dictionary d2=new AVLTree(q.address,q.size,q.size);
                int t=current.size+q.size;
                Dictionary newMem=new AVLTree(current.address,t,t);
                freeBlk.Delete(d1);
                freeBlk.Delete(d2);
                freeBlk.Insert(current.address,t,t);
                current.size+=q.size;
                tree.Delete(q);
            }else
                current=current.getNext();
        }
    }
}