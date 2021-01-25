// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List  next; // Next Node
    private A1List prev;  // Previous Node 

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }
    
    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key)
    {
            if(this.next==null)
                return null;
            A1List newNode= new A1List(address, size, key);
            newNode.next=this.next;
            this.next.prev=newNode;
            this.next=newNode;
            newNode.prev=this;
            return newNode;
    }
    private boolean isEqual(Dictionary d, A1List node){
        if(d.key==node.key && d.size==node.size && d.address==node.address)
            return true;
        return false;
    }
    public boolean Delete(Dictionary d) 
    {
        A1List current= this.getFirst();
        boolean f=false;
        if(current==null)
            return false;
        else{
            while(current.next.next!=null){
                if(isEqual(d,current)){
                    f=true;
                    current.prev.next=current.next;
                    current.next.prev=current.prev;
                }
                current=current.next;
            }
            if(isEqual(d,current)){
                current.prev.next=current.next;
                current.next.prev=current.prev;
                current.next=null;
                current.prev=null;
                current=null;
                return true;
            }
        }
        return f;
        
    }
    public A1List Find(int k, boolean exact)
    { 
        A1List current= this.getFirst();
        if(current==null)
            return null;
        if(exact){
            while(current.next.next!=null){
                if(current.key==k)
                    return current;
                current=current.next;
            }
            if(current.key==k)
                return current;
            else return null;
        }
        else{
            while(current.next.next!=null){
                if(current.key>=k)
                    return current;
                current=current.next;
            }
            if(current.key>=k)
                return current;
            else return null;
        }
       
    }

    public A1List getFirst()
    {
        A1List current=this;
        if(current.prev==null && current.next.next==null)
            return null;
        else if(current.prev==null &&current.next.next!=null)
            return current.next;
        if(current.next==null && current.prev.prev==null)
            return null;
        while(current.prev.prev!=null)
            current=current.prev;
        return current;
        
    }
    public A1List getLast(){
        A1List current=this.getFirst();
        if(current==null)
            return null;
        while(current.next.next!=null)
            current=current.next;
        return current;
    }
    public A1List getNext() 
    {
        
        if(this.next==null || this.next.next==null)
            return null;
        else
            return this.next;
        
    }

    public boolean sanity()
    {
        A1List current=this;
        A1List hare=current;
        while(hare!=null && hare.next!=null){
            hare=hare.next.next;
            current=current.next;
            if(current==hare)
                return false;
        }
        hare=this;
        current=this;
        while(hare!=null && hare.prev!=null){
            hare=hare.prev.prev;
            current=current.prev;
            if(current==hare)
                return false;
        }
        current=this;
        while(current.next!=null){
            if(current.next.prev!=current)
                return false;
            current=current.next;
        }
        if(current.address!=-1 || current.key!=-1 || current.size!=-1)
            return false;
        current=this;
        while(current.prev!=null){
            if(current.prev.next!=current)
                return false;
            current=current.prev;
        }
        if(current.address!=-1 || current.key!=-1 || current.size!=-1)
            return false;
        return true;
    }

}


