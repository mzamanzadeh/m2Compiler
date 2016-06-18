import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Meysam & Mohammad on 6/3/2016.
 */
public class CG
{
    TacCode[] code;
    int pc;
    int t;
    SymbolTable symbolTable;
    int label;
    Stack<Integer> labelStack;
    ArrayList<Integer> dclNoTypeAssigned;
    CG(){
        code = new TacCode[500];
        dclNoTypeAssigned=new ArrayList<Integer>();
        labelStack=new Stack<Integer>();
        symbolTable = new SymbolTable();
        pc = 0;
        t = 0;
        label=0;
    }

    void arithmeticOperand(String op,String res,String a , String b )
    {
        code[pc] = new TacCode(op,res,a,b);
        System.out.println(code[pc]);
        pc++;
    }

    void assign(String a , String b)
    {
        if(symbolTable.lookup(a)==null)
            m2Error(a + " not declared in this scope");
        if(b.matches("[a-zA-Z_] [0-9a-zA-Z_]*") && symbolTable.lookup(b)==null)
            m2Error(b + " not declared in this scope");

        code[pc]=new TacCode("=",a,b);
        System.out.println(code[pc]);
        pc++;
    }

    void declarationNoType(String name)
    {
        if(symbolTable.lookup(name)!=null)
            m2Error("redeclare: " + name);
        symbolTable.insert(name,0);
        code[pc]=new TacCode("VAR",name,"");
        dclNoTypeAssigned.add(pc);
        pc++;
    }
    void declarationSetType(String type)
    {
        int sizeType=Integer.parseInt(type);

        for(int i:dclNoTypeAssigned){
            code[i].opr2=type;
            symbolTable.table.get(code[i].opr1).setSize(sizeType);
            System.out.println(code[i]);
        }
        this.dclNoTypeAssigned.clear();
    }

    void functionDeclaration(String name,String sizeOfOutput){
        code[pc] = new TacCode("FUN",name,"");
        if(!sizeOfOutput.equals("0"))
            code[pc].opr2 = sizeOfOutput;
        pc++;
        System.out.println(code[pc-1]);
    }

    void endFunction(String name){
        code[pc] = new TacCode("END",name,"");
        pc++;
        System.out.println(code[pc-1]);
    }

    void argument(String size,String name,String dim){
        code[pc] = new TacCode("ARG");
        code[pc].opr1 = size;
        String a[] = dim.split(",");
        int diim = 1;
        if(!dim.equals("0")){
            for(int i = 1; i < a.length;i++){
                diim *= Integer.parseInt(a[i]);
            }
            code[pc].opr2 = new Integer(diim).toString();
            code[pc].opr3 = name;
        }
        else
            code[pc].opr2 = name;
        System.out.println(code[pc]);
        pc++;
    }

    void retStm(String retVal){
        code[pc] = new TacCode("RET",retVal,"");
        pc++;
        System.out.println(code[pc-1]);
    }

    void ifState(String id)
    {
        code[pc]=new TacCode("JNZ","Label"+label,id);
        labelStack.push(label++);
        System.out.println(code[pc]);
        pc++;
    }

    void endIfState()
    {
        code[pc]=new TacCode("LB","Label"+Integer.toString(labelStack.pop()),"");
        System.out.println(code[pc]);
        pc++;
    }

    void startElse()
    {
        int ii=labelStack.pop();
        code[pc]=new TacCode("JMP","Label"+label,"");
        labelStack.push(label++);
        System.out.println(code[pc]);
        pc++;

        code[pc]=new TacCode("LB","Label"+Integer.toString(ii),"");
        System.out.println(code[pc]);
        pc++;
    }

    void endElse()
    {
        code[pc]=new TacCode("LB","Label"+Integer.toString(labelStack.pop()),"");
        System.out.println(code[pc]);
        pc++;
    }

    void for_start()
    {
        code[pc]=new TacCode("LB","Label"+label,"");
        labelStack.push(label++);
        System.out.println(code[pc]);
        pc++;
    }

    void for_middle(String id )
    {
        code[pc]=new TacCode("JNZ","Label"+label,id);
        labelStack.push(label++);
        System.out.println(code[pc]);
        pc++;
    }

    void for_end()
    {
        int ll=labelStack.pop();
        code[pc]=new TacCode("JMP","Label"+Integer.toString(labelStack.pop()),"");
        System.out.println(code[pc]);
        pc++;

        code[pc]=new TacCode("LB","Label"+Integer.toString(ll),"");
        System.out.println(code[pc]);
        pc++;
    }

    void repeat_start()
    {
        code[pc]=new TacCode("LB","Label"+label,"");
        labelStack.push(label++);
        System.out.println(code[pc]);
        pc++;
    }

    void repeat_end(String id)
    {
        code[pc]=new TacCode("JZ","Label"+Integer.toString(labelStack.pop()),id);
        System.out.println(code[pc]);
        pc++;
    }

    /*void isDeclaredGlobaly(String id)
    {
        if(symbolTable.lookup(id)==null)
            m2Error( id + " not declared!!");

    }*/

    void m2Error(String err)
    {
        System.err.println("--error: " +err);
        System.exit(0);
    }
}
