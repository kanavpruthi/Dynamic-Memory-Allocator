// Class: Height balanced AVL Tree
// Binary Search Tree
import java.util.*;
public class AVLTree extends BSTree {    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
    
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.
    private int getHeight(AVLTree node){
        if(node==null) return -1;
        return node.height;
    }
    private int balanceFactor(AVLTree node){
        if(node==null) return 0;
        else return getHeight(node.left) - getHeight(node.right);
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
    private AVLTree rightRotate(AVLTree y){
        if(y.parent==null) return y;
        AVLTree x=y.left;
        AVLTree t=x.right;
        x.parent=y.parent;
        if(y.parent.right==y)
            y.parent.right=x;
        else y.parent.left=x;
        y.parent=x; 
        x.right=y;
        y.left=t;
        if(t!=null)
            t.parent=y;
        y.height=Math.max(getHeight(y.left),getHeight(y.right))+1;
        x.height=Math.max(getHeight(x.left),getHeight(x.right))+1;
        return x;
    }
    private AVLTree leftRotate(AVLTree y){
        if(y.parent==null) return y;
        AVLTree x=y.right;
        AVLTree t=x.left;
        x.parent=y.parent;
        if(y.parent.right==y)
            y.parent.right=x;
        else y.parent.left=x;
        y.parent=x;
        x.left=y;
        y.right=t;
        if(t!=null)
            t.parent=y;
        y.height=Math.max(getHeight(y.left),getHeight(y.right))+1;
        x.height=Math.max(getHeight(x.left),getHeight(x.right))+1;
        return x;
    }
    private AVLTree help_insert(AVLTree node, AVLTree root){
        if(isGreater(node,root)){
            if(root.right!=null) return help_insert(node,root.right);
            else{
                root.right=node;
                node.parent=root;
                return node;
            }
        }
        else{
            if(root.left!=null) return help_insert(node,root.left);
            else{
                root.left=node;
                node.parent=root;
                return node;
            }
        }  
        
    }
    public AVLTree Insert(int address, int size, int key){ 
        AVLTree current=this;
        AVLTree newNode=new AVLTree(address,size,key);
        if(current.parent==null && current.right==null){
            current.right=newNode;
            newNode.parent=current;
            return newNode;
        }
        if(current.parent==null)
            current=current.right;
        while(current.parent.parent!=null)
            current=current.parent;
        newNode=help_insert(newNode,current);
        AVLTree x=newNode;
        while(newNode.parent!=null){
            newNode.height=1+Math.max(getHeight(newNode.left),getHeight(newNode.right));
            int bf=balanceFactor(newNode);
            if(bf>1 && balanceFactor(newNode.left)>=0)//Left Left case
                newNode=rightRotate(newNode);
            if(bf<-1 && balanceFactor(newNode.right)<=0)    //Right right case
                newNode=leftRotate(newNode);
            if(bf > 1 && balanceFactor(newNode.left)<0){   // Left Right case
                    newNode.left=leftRotate(newNode.left);
                    newNode=rightRotate(newNode);
            }   
            if(bf < -1 && balanceFactor(newNode.right)>0){// Right Left case
                    newNode.right=rightRotate(newNode.right);
                    newNode=leftRotate(newNode);
            }
            newNode=newNode.parent;
        }
        return x;
    }
    private AVLTree del_help(AVLTree current){
        AVLTree x =current;
        if(current.left==null && current.right==null){
            if(current.parent.right==current)
                current.parent.right=null;
            else
                current.parent.left=null;
            x=current.parent;
            current.parent=null;
            return x;
        }
        else if(current.left==null){
            if(current.parent.right==current){
                current.parent.right=current.right;
                current.right.parent=current.parent;
            }else{
                current.parent.left=current.right;
                current.right.parent=current.parent;
            }
            x=current.parent;
            current.parent=null;
            return x;
        }
        else if(current.right==null){
            if(current.parent.right==current){
                current.parent.right=current.left;
                current.left.parent=current.parent;
            }else{
                current.parent.left=current.left;
                current.left.parent=current.parent;
            }
            x=current.parent;
            current.parent=null;
            return x;
        }
        else{
            AVLTree base=current;
            current=current.getNext();
            base.key=current.key;
            base.address=current.address;
            base.size=current.size;
            return del_help(current);
        }
        
    }

    public boolean Delete(Dictionary e)
    {
        if(e==null)
            return false;
        AVLTree current=this;
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
        current=del_help(current);
        while(current.parent!=null){
            current.height=1+Math.max(getHeight(current.left),getHeight(current.right));
            int bf=balanceFactor(current);
            if(bf>1 && balanceFactor(current.left)>=0)//Left Left case
                current=rightRotate(current);
            if(bf<-1 && balanceFactor(current.right)<=0)    //Right right case
                current=leftRotate(current);
            if(bf > 1 && balanceFactor(current.left)<0){   // Left Right case
                    current.left=leftRotate(current.left);
                    current=rightRotate(current);
            }   
            if(bf < -1 && balanceFactor(current.right)>0){// Right Left case
                    current.right=rightRotate(current.right);
                    current=leftRotate(current);
            }
            current=current.parent;
        }
        return true;
    }
        
    private AVLTree help_true(AVLTree current, int k){
        if(current==null)
            return null;
        else if(current.key<k) return help_true(current.right,k);
        else if(current.key>k) return help_true(current.left,k);
        else{
            AVLTree min=help_true(current.left,k);
            if(min!=null)
                return min;
            else return current;
        }
    }
    private AVLTree help_false(AVLTree current,int k){
        if(current==null)
            return null;
        else if(current.key<k) return help_false(current.right,k);
        else{
            AVLTree min=help_false(current.left,k);
            if(min!=null)
                return min;
            else return current;
        }
    }
    public AVLTree Find(int k, boolean exact)
    {
        AVLTree current=this;
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

    public AVLTree getFirst()
    { 
        AVLTree current=this;
        if(current.parent==null && current.right==null)
            return null;
        if(current.parent==null)
            current=current.right;
        while(current.left!=null)
            current=current.left;
        return current;
    }

    public AVLTree getNext()
    {
        AVLTree current = this;
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
    public boolean sanity(){
        if(this.isCyclic())
            return false;
        AVLTree current=this;
        if(current==null)return true;
        while(current.parent!=null)current=current.parent; //Current reaches sentinel now.
        if(current.key!=-1 || current.address!=-1 || current.size!=-1 || current.left!=null)
            return false;
        if(current.right==null) return true;
        if(relationBalanced(current)==false)
            return false;
        current=this.getFirst();
        while(current.getNext()!=null){
            if(isGreater(current,current.getNext())){
                return false;
            }
            current=current.getNext();
        }
        while(current.parent!=null)current=current.parent; //Current reaches sentinel now.
        current=current.right;
        if(heightBalanced(current)==false)
            return false;
        return true;
    }
    private boolean heightBalanced(AVLTree node){
        if (node == null)
            return true;
        int bf=balanceFactor(node);
        if (bf > 1)
            return false;
        else if (bf<-1)
            return false;
        return heightBalanced(node.left) && heightBalanced(node.right);
    }
    private boolean relationBalanced(AVLTree node){
        if(node==null ||(node.left==null && node.right==null))
            return true;
        else if(node.left==node.right)
            return false;
        else if(node.right != null && node.right.parent!=node )
                return false;
        else if(node.left != null && node.left.parent!=node )
                return false;
        return relationBalanced(node.right) && relationBalanced(node.left);
    }
    private boolean isCyclic(){
    
        AVLTree current=this;
        AVLTree hare=current;
        while(hare!=null && hare.parent!=null){
            hare=hare.parent.parent;
            current=current.parent;
            if(current==hare)
                return true;
        }
        return false;
    }
}