// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {
    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
        
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }
    private boolean isEqual(Dictionary d1, Dictionary d2){
            if(d1.key==d2.key && d1.address==d2.address && d1.size==d2.size)
                return true;
            return false;
    }
    private boolean isGreater(Dictionary b1, Dictionary b2){
        if(b1.key>b2.key)
            return true;
        else if(b2.key>b1.key)
            return false;
        else if(b1.address>b2.address)
            return true;
        else return false;
         
    }
    public BSTree Insert(int address, int size, int key) 
    { 
        BSTree current = this;
        BSTree newNode = new BSTree(address,size,key);
        if(current.parent==null && current.right==null){
            //Checks for sentinel node.
            current.right=newNode;
            newNode.parent = current;
            return newNode;
        }
        
        if(current.parent==null)
            current=current.right;
        while(current.parent.parent!=null)
            current=current.parent;
        
        while(true){
            if(isGreater(current,newNode)){
                if(current.left==null){
                    current.left=newNode;
                    newNode.parent=current;
                    return newNode;   
                }else current=current.left;
            }
            else{
                if(current.right==null){
                    current.right=newNode;
                    newNode.parent=current;
                    return newNode;
                }else current=current.right;
            }
        }
    }
    private void del_help(BSTree current){
        if(current.left==null && current.right==null){
            if(current.parent.right==current)
                current.parent.right=null;
            else
                current.parent.left=null;
            current.parent=null;
        }
        else if(current.left==null){
            if(current.parent.right==current){
                current.parent.right=current.right;
                current.right.parent=current.parent;
            }else{
                current.parent.left=current.right;
                current.right.parent=current.parent;
            }
            current.parent=null;
        }
        else if(current.right==null){
            if(current.parent.right==current){
                current.parent.right=current.left;
                current.left.parent=current.parent;
            }else{
                current.parent.left=current.left;
                current.left.parent=current.parent;
            }
            current.parent=null;
        }
        else{
            BSTree base=current;
            current=current.getNext();
            base.key=current.key;
            base.address=current.address;
            base.size=current.size;
            del_help(current);
        }     
    }
    public boolean Delete(Dictionary e)
    {
        if(e==null)
            return false;
        BSTree current=this;
        if(current.parent==null){
            if(current.right==null)
                return false;
            current=current.right;
        }
        
        while(current.parent.parent!=null)
            current=current.parent;        

        while(current!=null && !isEqual(e,current)){
            if(isGreater(e,current))
                current=current.right;
            else
                current=current.left;
        }
        
        if(current==null)
            return false;
        del_help(current);
        return true;
    }
    private BSTree help_true(BSTree current, int k){
        if(current==null)
            return null;
        else if(current.key<k) return help_true(current.right,k);
        else if(current.key>k) return help_true(current.left,k);
        else{
            BSTree min=help_true(current.left,k);
            if(min!=null)
                return min;
            else return current;
        }
    }
    private BSTree help_false(BSTree current,int k){
        if(current==null)
            return null;
        else if(current.key<k) return help_false(current.right,k);
        else{
            BSTree min=help_false(current.left,k);
            if(min!=null)
                return min;
            else return current;
        }
    }
    public BSTree Find(int k, boolean exact)
    {
        BSTree current=this;
        if(this.parent==null && this.right==null)
            return null;
        if(this.parent==null)
            current=current.right;
        while(current.parent.parent!=null)
            current=current.parent;
        if(exact)
            return help_true(current,k);
        else
            return help_false(current,k);
        
    }

    public BSTree getFirst()
    { 
        BSTree current=this;
        if(current.parent==null && current.right==null)
            return null;
        if(current.parent==null)
            current=current.right;
        while(current.left!=null)
            current=current.left;
        return current;
    }
    
    public BSTree getNext()
    {
        BSTree current = this;
        if(current.parent==null)
            return null;
        if(current.right!=null){
            current=current.right;
            while(current.left!=null)
                current=current.left;
            return current;
        }
        else{
            while(current.parent!=null){
                if(current.parent.left==current)
                    return current.parent;     
                current=current.parent;
            }
            return null;
        }
    }
    // public boolean sanity(){
        // System.out.println(this.right.key);
        // System.out.println(this.right.left.key);
        // //System.out.println(this.right.right.key);
        // return true;
    // }
    public boolean sanity(){ 
        BSTree current=this;
        current=this;
        if(current==null)
            return true;
        if(current.parent==null && current.right==null)
            return true;
        current=this;
        BSTree hare=current;
        while(hare!=null && hare.parent!=null){
            hare=hare.parent.parent;
            current=current.parent;
            if(current==hare)
                return false;
        }
        current=this;
        if(current.parent==null)
            current=current.right;
        while(current.parent.parent!=null)
            current=current.parent;
        //Now we are at the root.
        if(helper(current)==false)
            return false;
        current=this.getFirst();
        while(current.getNext()!=null){
            if(isGreater(current.getNext(),current)==false)
                return false;
            current=current.getNext();
        }
        return true;       
    }
    private boolean helper(BSTree root){
        if(root==null)
            return true;
        if(root.left==null && root.right==null)
            return true;
        else if(root.left==null){
            if(root.right.parent==root)
                return helper(root.right);
            else return false;
        }    
        else if(root.right==null){
            if(root.left.parent==root)
                return helper(root.left);
            else return false;
        }    
        else{
            if(root.right.parent==root && root.left.parent==root)
                return helper(root.left) && helper(root.right);
            else return false;
        }
    }
}


 


