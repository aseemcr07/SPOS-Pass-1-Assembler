import java.io.File;
import java.util.Scanner;

// import javax.lang.model.util.ElementScanner14;

import java.io.FileNotFoundException;

class node {
    String minemonic;
    int op_code;
    node next;

    node(String a, int b) {
        minemonic = a;
        op_code = b;

    }
}

class node_code {
    String label;
    String directive;
    String regs;
    String literal;

    node_code next;

    node_code(String a, String b, String c, String d) {
        label = a;
        directive = b;
        regs = c;
        literal = d;

    }

}
class node_inter {
    int address;
    String cl;
    int cl_op;
    int regs_op;
    int lit_ind;
    int sym_ind;
    node_inter next;
    node_inter (int a, String b, int c,int d,int e,int f)
    {
        address = a;
        cl= b;
        cl_op = c;
        regs_op = d;
        lit_ind = e;
        sym_ind = f;

    }
}

class write_read_table {
    // CONTAIN INPUT
    node Is;
    node Ad;
    node Dc;
    node regs;
    node_code code;

    // CONTAIN OUTPUT
    node literal;
    node symbol;
    node_inter inter_mediate;

    public node inst(node head, String a, int b) {
        node new_node = new node(a, b);
        new_node.next = null;
        if (head == null) {
            head = new_node;
        } else {
            node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = new_node;
        }
        return head;
    }
    public node_inter intermediate(node_inter head, int a, String b, int c, int d, int e, int f) {
        node_inter new_node = new node_inter(a, b, c, d, e, f);
        new_node.next = null;
        if (head == null) {
            head = new_node;
        } else {
            node_inter temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = new_node;
        }
        return head;
    }

    public void display(node head) {

        while (head != null) {
            System.out.println("minemoni: " + head.minemonic + " opcode: " + head.op_code);

            head = head.next;
        }
    }

    public node_code inst_code(node_code head, String a, String b, String c, String d) {
        node_code new_node = new node_code(a, b, c, d);
        new_node.next = null;
        if (head == null) {
            head = new_node;
        } else {
            node_code temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = new_node;
        }
        return head;
    }

    public void display_code(node_code head) {

        while (head != null) {
            System.out.println("label: " + head.label + " directives; " + head.directive + "Regs: " + head.regs
                    + " literal: " + head.literal);
            head = head.next;
        }
    }

    public void read_table(String a, int flag) {
        try {

            File myObj = new File(a);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {

                String data = myReader.nextLine();
                String[] newString = data.split("-");
                String str = newString[1];
                int b = Integer.valueOf(newString[0]);
                if (flag == 1) {

                    Is = inst(Is, str, b);
                }

                else if (flag == 2) {
                    Dc = inst(Dc, str, b);
                }

                else if (flag == 3) {
                    Ad = inst(Ad, str, b);
                } else if (flag == 4) {
                    regs = inst(regs, str, b);
                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void read_code() {
        try {
            File mycode = new File("input-code.txt");
            Scanner myreader = new Scanner(mycode);
            while (myreader.hasNextLine()) {
                String data = myreader.nextLine();
                System.out.println(data);
                if (data.contains("PRINT")) {
                    String[] newstring = data.split(" ");
                    System.out.println("print is working " + newstring[1]);
                    String label = newstring[0];
                    String directives = newstring[1];
                    String regs = null;
                    String literal = newstring[2];
                    code = inst_code(code, label, directives, regs, literal);

                } else if (data.contains(",")) {
                    String[] newstring = data.split(" ");
                    String[] newString_2 = newstring[2].split(",");
                    String label = newstring[0];
                    String directives = newstring[1];
                    String regs = newString_2[0];
                    String literal = newString_2[1];
                    if (label == "") {
                        label = null;
                    }
                    code = inst_code(code, label, directives, regs, literal);
                } else {
                    String[] newstring = data.split(" ");
                    System.out.println(newstring[1]);

                    if (newstring[1].contains("LTORG")) {
                        String label = null;
                        String directives = newstring[1];
                        String regs = null;
                        String literal = null;
                        code = inst_code(code, label, directives, regs, literal);
                    } else if (newstring[1].contains("END")) {
                        String label = null;
                        String directives = newstring[1];
                        String address = null;
                        String literal = null;
                        code = inst_code(code, label, directives, address, literal);

                    } else {
                        String label = newstring[0];
                        String directives = newstring[1];
                        String address = newstring[2];
                        String literal = null;
                        if (label == "") {
                            label = null;
                        }
                        code = inst_code(code, label, directives, address, literal);
                    }

                }

            }

            myreader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void table_file() {
        read_table("imperative-statements.txt", 1);
        read_table("declaration-statements.txt", 2);
        read_table("assembler-directives.txt", 3);
        read_table("register-opcodes.txt", 4);
        String dash = "-";
        String t_1 = "Impertive Statement Table";
        String t_2 = "Assembler Directive Table";
        String t_3 = "Declaration Statement Table";
        String t_4 = "Register Table";
        String t_5 = "read code";
        String t_6 = "Output (Pass_1)";
        String t_7 = "Literal Table";
        String t_8 = "Literal Table";
        String t_9 = "Symbol Table";

        System.out.println(
                dash.repeat(40)+t_1+dash.repeat(60-t_1.length()));
        display(Is);
        System.out.println(
                dash.repeat(40)+t_2+ dash.repeat(60-t_2.length()));
        display(Ad);
        System.out.println(
                dash.repeat(40)+t_3+ dash.repeat(60-t_3.length()));
        display(Dc);
        System.out.println(
                dash.repeat(40)+t_4 +dash.repeat(60-t_4.length()));
        display(regs);
        System.out.println(
                dash.repeat(40)+t_5 +dash.repeat(60-t_5.length()));
        read_code();
        
        System.out.println(
                dash.repeat(40)+t_6+ dash.repeat(60-t_6.length()));
        pass_1();
        System.out.println(
                dash.repeat(40)+t_7+ dash.repeat(60-t_7.length()));
        display_inter(inter_mediate);
        System.out.println(
                dash.repeat(40)+t_8+ dash.repeat(60-t_8.length()));
        display_literal(literal);
        System.out.println(
                dash.repeat(40)+t_9 + dash.repeat(60-t_9.length()));
        display_symbol(symbol);

    }

    public boolean present(node table, String src) {
        while (table != null) {

            if (table.minemonic.equals(src)) {
                return true;
            }
            table = table.next;
        }
        return false;
    }

    public int ret_op_code(node table, String src) {
        int a = 0;
        while (table != null) {
            if (table.minemonic.equals(src)) {
                a = table.op_code;
            }
            table = table.next;
        }
        return a;
    }

    public void pass_1() {
        node_code src = code;
        node table_is = Is;
        node table_ad = Ad;
        node table_dc = Dc;
        node table_reg = regs;
        int op_code = 0;
        
        int a = 0;
        if (src.directive.equals("START")) {
            a = Integer.parseInt(src.regs);
            
            while (src != null) {
                
                String cl = null;
                int cl_op = 0;
                int regs_op = 0;
                int lit_index = 0;
                int sym_index = 0;

                
                // we are find that head.directive is Is, Ad, Dc or register in this condition.

                // this conditions are for imperative statement.
                if (present(table_is, src.directive)) {
                    op_code = ret_op_code(table_is, src.directive);
                    cl = "IS";
                    
                }

                else if (present(table_ad, src.directive)) {

                    if (src.directive.contains("START")) {
                        // System.out.println("Ltorg is working");
                        op_code = ret_op_code(table_ad, src.directive);
                        

                    } else if (src.directive.contains("ORIGIN")) 
                    {
                        a = Integer.valueOf(src.regs);
                    } 
                    else if (src.directive.contains("LTORG")) {
                        // System.out.println("Ltorg is working");
                        op_code = ret_op_code(table_ad, src.directive);
                        node temp = literal;
                        while (temp != null) {
                            if (temp.op_code == 0) {
                                temp.op_code = a++;
                            }
                           temp = temp.next;
                        }
                        
                    }
                    else {
                        op_code = ret_op_code(table_ad, src.directive);
                    }
                    cl = "AD";
                }
                else if (present(table_dc, src.directive)) {
                    op_code = ret_op_code(table_dc, src.directive);
                    node temp = symbol;
                    while (temp != null) {
                        if (temp.minemonic.equals(src.label)) {
                            temp.op_code = a;
                        }
                        temp = temp.next;
                    }

                    cl = "DL";       

                }
                // this conditions are for register.
                if (present(table_reg, src.regs)) {
                    op_code = ret_op_code(table_reg, src.regs);
                    regs_op = op_code;
                }

                // this condition is for symbol
                if (src.label == null) {
                    System.out.print("");
                } 
                else if (present(symbol, src.label)) {
                    System.out.print("");
                } 
                else {
                    if (src.directive.contains("EQU")) {

                        int temp_a = 0;
                        temp_a = ret_op_code(symbol, src.regs);
                        
                        
                        symbol = inst(symbol, src.label, temp_a);
                        sym_index++;
                    } else {
                        if (src.label.contains(":")) {

                            String s = src.label.replace(":", "");
                            
                            symbol = inst(symbol, s, a);
                            sym_index++;
                        } else if (src.label != "") {
                            symbol = inst(symbol, src.label, a);
                            sym_index++;
                        }

                    }

                }

                // this condition is for literals
                if (src.literal == null) {
                    System.out.print("");

                } else if (src.literal.contains("=")) {
                    if (present(literal, src.literal)) {
                        System.out.print("");
                    } else {
                        literal = inst(literal, src.literal, 0);
                        lit_index++;
                    }

                } else {
                    symbol = inst(symbol, src.literal, 0);
                    lit_index++;
                }

                cl_op = op_code;

                inter_mediate = intermediate(inter_mediate, a, cl, cl_op, regs_op, lit_index, sym_index);
                

                if (src.directive.contains("START")) {
                    System.out.println("");
                }

                else if (present(Ad, src.directive)) {
                    
                } else if (src.directive != " " ) {
                    a++;
                }
                



              
                src = src.next;

                

            }

        }

    }
    
    public int no_digit(int a)
    {
        String a_1 = String.valueOf(a);
        int sp = a_1.length();
        return sp;
    }
    public void display_inter(node_inter head) {
        node_code temp = code;
        String space = " ";
        System.out.println("Directive"+space.repeat(5)+"Adress"+space.repeat(5)+" Class "+space.repeat(5)+" register "+space.repeat(5)+" literal "+space.repeat(5)+" Symbol " );
        
        while (head != null) {

           
         
            int leng_1= no_digit(head.address);
            int leng_2 = temp.directive.length();
            int leng_3 = no_digit(head.cl_op);
            int leng_4 = no_digit(head.regs_op);
            int leng_5 = no_digit(head.lit_ind);

            
            System.out.println(temp.directive+space.repeat(15-leng_2)+head.address+space.repeat(12-leng_1) + head.cl+space.repeat(1) + head.cl_op+space.repeat(11-leng_3) + head.regs_op+space.repeat(15-leng_4) + head.lit_ind+space.repeat(13-leng_5) + head.sym_ind);
            temp= temp.next;
            head = head.next;
        }
    }

    public void display_literal(node head) {
        String space = " ";
        System.out.println("Literal"+space.repeat(5) + "address" );
        
        while (head != null) {
            int lit_leng = head.minemonic.length();
            
            System.out.println(head.minemonic+space.repeat(12-lit_leng) + head.op_code+" ");

            head = head.next;
        }
    }

    public void display_symbol(node head) {

        String space = " ";
        System.out.println("Symbol"+space.repeat(5) + "address" );
        
        while (head != null) {
            int lit_leng = head.minemonic.length();
            
            System.out.println(head.minemonic+space.repeat(11-lit_leng) + head.op_code+" ");

            head = head.next;
        }
    }
}

public class PassOne {
    public static void main(String[] args) {

        write_read_table one = new write_read_table();
        one.table_file();
    }
}