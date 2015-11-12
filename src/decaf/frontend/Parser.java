//### This file created by BYACC 1.8(/Java extension  1.13)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//###           14 Sep 06  -- Keltin Leung-- ReduceListener support, eliminate underflow report in error recovery
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 11 "Parser.y"
package decaf.frontend;

import decaf.tree.Tree;
import decaf.tree.Tree.*;
import decaf.error.*;
import java.util.*;
//#line 25 "Parser.java"
interface ReduceListener {
  public boolean onReduce(String rule);
}




public class Parser
             extends BaseParser
             implements ReduceListener
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

ReduceListener reduceListener = null;
void yyclearin ()       {yychar = (-1);}
void yyerrok ()         {yyerrflag=0;}
void addReduceListener(ReduceListener l) {
  reduceListener = l;}


//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:SemValue
String   yytext;//user variable to return contextual strings
SemValue yyval; //used to return semantic vals from action routines
SemValue yylval;//the 'lval' (result) I got from yylex()
SemValue valstk[] = new SemValue[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new SemValue();
  yylval=new SemValue();
  valptr=-1;
}
final void val_push(SemValue val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    SemValue[] newstack = new SemValue[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final SemValue val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final SemValue val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short VOID=257;
public final static short BOOL=258;
public final static short INT=259;
public final static short STRING=260;
public final static short CLASS=261;
public final static short NULL=262;
public final static short EXTENDS=263;
public final static short THIS=264;
public final static short WHILE=265;
public final static short FOR=266;
public final static short IF=267;
public final static short ELSE=268;
public final static short RETURN=269;
public final static short BREAK=270;
public final static short NEW=271;
public final static short PRINT=272;
public final static short READ_INTEGER=273;
public final static short READ_LINE=274;
public final static short LITERAL=275;
public final static short IDENTIFIER=276;
public final static short AND=277;
public final static short OR=278;
public final static short UMINUS=279;
public final static short STATIC=280;
public final static short INSTANCEOF=281;
public final static short NUMINSTANCES=282;
public final static short LESS_EQUAL=283;
public final static short GREATER_EQUAL=284;
public final static short EQUAL=285;
public final static short NOT_EQUAL=286;
public final static short SELF_PLUS=287;
public final static short SELF_MINUS=288;
public final static short EMPTY=289;
public final static short FI=290;
public final static short DO=291;
public final static short OD=292;
public final static short GUARD=293;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    2,    6,    6,    7,    7,    7,    9,    9,   10,
   10,    8,    8,   11,   12,   12,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   14,   14,   14,
   26,   26,   23,   23,   25,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   28,   28,   27,
   27,   29,   29,   16,   17,   20,   15,   30,   30,   18,
   18,   19,   21,   22,   31,   31,   32,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    2,
    3,    6,    2,    0,    2,    2,    0,    1,    0,    3,
    1,    7,    6,    3,    2,    0,    1,    2,    1,    1,
    1,    2,    2,    2,    1,    1,    1,    3,    1,    0,
    2,    0,    2,    4,    5,    1,    1,    1,    2,    2,
    2,    2,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    2,    2,    5,    3,
    3,    1,    4,    5,    6,    4,    5,    1,    1,    1,
    0,    3,    1,    5,    9,    1,    6,    2,    0,    2,
    1,    4,    3,    3,    3,    1,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   13,   17,
    0,    7,    8,    6,    9,    0,    0,   12,   15,    0,
    0,   16,   10,    0,    4,    0,    0,    0,    0,   11,
    0,   21,    0,    0,    0,    0,    5,    0,    0,    0,
   26,   23,   20,   22,    0,   79,   72,    0,    0,    0,
    0,   86,    0,    0,    0,    0,   78,    0,    0,    0,
    0,    0,    0,    0,   24,    0,   27,   35,   25,    0,
   29,   30,   31,    0,    0,    0,   36,   37,    0,    0,
    0,    0,   48,    0,    0,    0,   46,    0,   47,    0,
   96,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   28,   32,   33,   34,
    0,    0,    0,    0,    0,    0,    0,   51,   52,    0,
    0,    0,    0,    0,    0,    0,   41,    0,    0,    0,
    0,    0,    0,   26,   93,    0,    0,    0,    0,    0,
   70,   71,    0,    0,    0,   66,   94,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   95,   73,
    0,    0,   92,    0,   76,    0,   44,    0,    0,    0,
   84,    0,    0,   74,    0,    0,   77,    0,   45,    0,
    0,   87,   75,    0,   88,    0,   85,
};
final static short yydgoto[] = {                          2,
    3,    4,   67,   20,   33,    8,   11,   22,   34,   35,
   68,   45,   69,   70,   71,   72,   73,   74,   75,   76,
   77,   78,   87,   80,   89,   82,  179,   83,  140,  192,
   90,   91,
};
final static short yysindex[] = {                      -253,
 -259,    0, -253,    0, -239,    0, -248,  -89,    0,    0,
 -105,    0,    0,    0,    0, -240,  116,    0,    0,   -7,
  -87,    0,    0,  -82,    0,   18,  -26,   22,  116,    0,
  116,    0,  -77,   50,   58,   65,    0,  -13,  116,  -13,
    0,    0,    0,    0,    4,    0,    0,   71,   73,  139,
  160,    0, -164,   75,   77,   78,    0,   83,   88,  160,
  160,  160,  160,   97,    0,  160,    0,    0,    0,   60,
    0,    0,    0,   72,   76,   81,    0,    0,   86,  781,
    0, -143,    0,  160,  160,   97,    0,  492,    0, -245,
    0,  781,   96,   47,  160,  103,  107,  160, -127,  -31,
  -31,  -41,  -41, -126,  516, -184,    0,    0,    0,    0,
  160,  160,  160,  160,  160,  160,  160,    0,    0,  160,
  160,  160,  160,  160,  160,  160,    0,  160,  160,  117,
  528,  100,  552,    0,    0,  160,  119,  118,  781,   36,
    0,    0,  579,  120,  123,    0,    0,  781,  954,  947,
   -4,   -4,   79,   79,  -36,  -36,  -41,  -41,  -41,   -4,
   -4,  591,  613,  160,   39,  160,   74,   39,    0,    0,
  694,  160,    0, -111,    0,  160,    0,  160,  125,  127,
    0,  865, -101,    0,  781,  132,    0,  904,    0,  160,
   39,    0,    0,  135,    0,   39,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  177,    0,   55,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  121,    0,    0,  140,    0,
  140,    0,    0,    0,  141,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -57,    0,    0,    0,    0,  -93,
  -56,    0,    0,    0,    0,    0,    0,    0,    0,  -93,
  -93,  -93,  -93,  -93,    0,  -93,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  892,    0,
  465,    0,    0,  -93,  -57,  -93,    0,    0,    0,    0,
    0,  126,    0,    0,  -93,    0,    0,  -93,    0,  981,
 1034, 1061, 1156,    0,    0,    0,    0,    0,    0,    0,
  -93,  -93,  -93,  -93,  -93,  -93,  -93,    0,    0,  -93,
  -93,  -93,  -93,  -93,  -93,  -93,    0,  -93,  -93,  408,
    0,    0,    0,    0,    0,  -93,    0,  -93,   45,    0,
    0,    0,    0,    0,    0,    0,    0,  -19,   10,  -28,
  151, 1103,   12,   41, 1290, 1314, 1210, 1234, 1263, 1327,
 1338,    0,    0,  -23,  -57,  -93,  435,   61,    0,    0,
    0,  -93,    0,    0,    0,  -93,    0,  -93,    0,  145,
    0,    0,  -33,    0,   57,    0,    0,  -12,    0,  -20,
  -57,    0,    0,    0,    0,  -57,    0,
};
final static short yygindex[] = {                         0,
    0,  184,  179,   35,    8,    0,    0,    0,  157,    0,
   38,   62, -108,  -58,    0,    0,    0,    0,    0,    0,
    0,    0,  -22, 1567,  324,    0,    0,    0,   27,    0,
  136,   67,
};
final static int YYTABLESIZE=1745;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         89,
  124,   40,   91,   27,  127,  122,   89,    1,   27,  127,
  123,   89,   65,   27,  127,   65,    5,   81,   21,   18,
   40,   38,   79,    7,   24,   89,  132,    9,   69,   65,
   65,   69,  124,   10,   65,   23,   63,  122,  120,   38,
  121,  127,  123,   64,  135,   69,   69,  136,   62,  128,
   64,   25,   58,   64,  128,   58,  181,   29,  183,  128,
   94,   31,   79,   32,   65,   32,   30,   64,   64,   58,
   58,   63,   64,   43,   58,   42,  173,   44,   64,  172,
   69,   59,  195,   62,   59,   83,  128,  197,   83,   89,
   38,   89,   12,   13,   14,   15,   16,   82,   59,   59,
   82,   39,   64,   59,   58,   40,   63,  147,  136,   41,
   84,   93,   85,   64,   95,  124,   96,   97,  107,   40,
  122,  120,   98,  121,  127,  123,   41,   99,   65,   63,
  108,  194,  130,   59,  109,  137,   64,  138,  126,  110,
  125,   62,   79,  141,   79,   79,  111,  142,  144,  145,
   63,   12,   13,   14,   15,   16,  164,   64,  166,  170,
  175,   41,   62,  176,  186,  189,  191,   79,   79,  128,
  172,   63,  193,   79,   17,  196,    1,   14,   86,    5,
   19,   18,   42,   62,   90,   80,    6,   36,   26,   19,
  180,   62,   63,   28,   62,  168,   41,    0,   37,   64,
    0,  106,  169,    0,   62,    0,    0,    0,   62,   62,
   30,    0,    0,   62,    0,    0,    0,    0,   42,   42,
    0,    0,    0,   89,   89,   89,   89,   89,   89,    0,
   89,   89,   89,   89,    0,   89,   89,   89,   89,   89,
   89,   89,   89,   62,    0,  118,  119,   89,   89,   65,
  118,  119,   42,   89,   89,   42,   89,   89,   89,   89,
   12,   13,   14,   15,   16,   46,    0,   47,   48,   49,
   50,    0,   51,   52,   53,   54,   55,   56,   57,    0,
    0,    0,  118,  119,   58,   59,   64,   64,   58,   58,
   60,   61,    0,    0,   66,   12,   13,   14,   15,   16,
   46,    0,   47,   48,   49,   50,    0,   51,   52,   53,
   54,   55,   56,   57,    0,    0,    0,   59,   59,   58,
   59,    0,    0,    0,    0,   60,   61,    0,    0,   66,
   12,   13,   14,   15,   16,   46,   42,   47,   48,   49,
   50,    0,   51,   52,   53,   54,   55,   56,   57,    0,
   97,    0,   97,   97,   58,   59,    0,  104,   46,    0,
   47,  114,  115,    0,   66,  118,  119,   53,   81,   55,
   56,   57,   12,   13,   14,   15,   16,   58,   59,   46,
    0,   47,    0,   60,   61,    0,    0,    0,   53,    0,
   55,   56,   57,    0,    0,    0,    0,    0,   58,   59,
   46,    0,   47,    0,   60,   61,    0,    0,   81,   53,
    0,   55,   56,   57,    0,    0,    0,    0,    0,   58,
   59,   46,    0,   47,    0,   60,   61,   62,   62,    0,
   53,    0,   55,   56,   57,   62,   62,    0,    0,    0,
   58,   59,    0,    0,   43,    0,   60,   61,   43,   43,
   43,   43,   43,   43,   43,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   43,   43,   43,   43,   43,
   43,   66,    0,    0,    0,    0,   66,   66,    0,   66,
   66,   66,    0,    0,    0,    0,    0,    0,   81,    0,
   81,   81,   66,   40,   66,    0,   66,   66,   43,    0,
   43,   47,    0,    0,    0,   39,   47,   47,    0,   47,
   47,   47,    0,   81,   81,    0,    0,    0,    0,   81,
    0,    0,    0,   39,   47,   66,   47,   47,  124,    0,
    0,    0,    0,  122,  120,    0,  121,  127,  123,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  134,
    0,  126,  124,  125,  129,   47,  146,  122,  120,    0,
  121,  127,  123,    0,  124,    0,    0,    0,  165,  122,
  120,    0,  121,  127,  123,  126,    0,  125,  129,    0,
    0,    0,  128,    0,    0,    0,    0,  126,  124,  125,
  129,    0,  167,  122,  120,    0,  121,  127,  123,    0,
    0,    0,    0,    0,    0,    0,  128,    0,    0,    0,
    0,  126,    0,  125,  129,  124,    0,    0,  128,    0,
  122,  120,  174,  121,  127,  123,    0,  124,    0,    0,
    0,    0,  122,  120,    0,  121,  127,  123,  126,    0,
  125,  129,  128,    0,    0,    0,    0,    0,    0,  124,
  126,    0,  125,  129,  122,  120,    0,  121,  127,  123,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  128,
  178,    0,  126,    0,  125,  129,    0,    0,    0,    0,
    0,  128,    0,  177,   43,   43,    0,    0,    0,    0,
   43,   43,   43,   43,   43,   43,    0,    0,    0,    0,
    0,    0,    0,  128,    0,    0,    0,    0,    0,    0,
   42,   66,   66,    0,    0,    0,    0,   66,   66,   66,
   66,   66,   66,    0,    0,    0,    0,    0,    0,    0,
  124,    0,    0,    0,    0,  122,  120,    0,  121,  127,
  123,   47,   47,    0,    0,    0,    0,   47,   47,   47,
   47,   47,   47,  126,    0,  125,  129,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  112,  113,
    0,    0,    0,    0,  114,  115,  116,  117,  118,  119,
    0,    0,    0,    0,  128,    0,  184,    0,    0,    0,
    0,    0,  112,  113,    0,    0,    0,    0,  114,  115,
  116,  117,  118,  119,  112,  113,    0,    0,    0,    0,
  114,  115,  116,  117,  118,  119,    0,  124,    0,    0,
    0,    0,  122,  120,    0,  121,  127,  123,  112,  113,
    0,    0,    0,    0,  114,  115,  116,  117,  118,  119,
  126,    0,  125,  129,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  112,  113,    0,    0,    0,
    0,  114,  115,  116,  117,  118,  119,  112,  113,    0,
    0,  128,    0,  114,  115,  116,  117,  118,  119,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  112,
  113,    0,    0,    0,    0,  114,  115,  116,  117,  118,
  119,  124,    0,    0,    0,    0,  122,  120,    0,  121,
  127,  123,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  190,  126,    0,  125,  129,   46,    0,
    0,    0,    0,   46,   46,    0,   46,   46,   46,    0,
  124,    0,    0,    0,    0,  122,  120,    0,  121,  127,
  123,   46,    0,   46,   46,  128,    0,    0,    0,    0,
    0,    0,    0,  126,    0,  125,    0,    0,    0,    0,
  112,  113,    0,    0,    0,    0,  114,  115,  116,  117,
  118,  119,   46,  124,    0,    0,    0,    0,  122,  120,
  124,  121,  127,  123,  128,  122,  120,    0,  121,  127,
  123,    0,    0,    0,    0,    0,  126,    0,  125,    0,
    0,    0,    0,  126,    0,  125,    0,   49,    0,    0,
    0,   49,   49,   49,   49,   49,    0,   49,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  128,   49,   49,
   49,    0,   49,   49,  128,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  112,  113,    0,
    0,    0,    0,  114,  115,  116,  117,  118,  119,    0,
   50,    0,    0,   49,   50,   50,   50,   50,   50,    0,
   50,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   50,   50,   50,    0,   50,   50,   67,    0,    0,
    0,   67,   67,   67,   67,   67,    0,   67,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   67,   67,
   67,    0,   67,   67,    0,    0,   50,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  112,  113,   63,    0,    0,   63,  114,  115,  116,
  117,  118,  119,   67,    0,    0,    0,    0,    0,    0,
   63,   63,    0,    0,    0,   63,    0,    0,   46,   46,
    0,    0,    0,    0,   46,   46,   46,   46,   46,   46,
  112,  113,    0,    0,    0,    0,  114,  115,  116,  117,
  118,  119,   68,    0,    0,   63,   68,   68,   68,   68,
   68,    0,   68,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   68,   68,   68,    0,   68,   68,    0,
    0,    0,    0,  112,    0,    0,    0,    0,    0,  114,
  115,  116,  117,  118,  119,    0,  114,  115,  116,  117,
  118,  119,    0,    0,    0,    0,   55,    0,   68,    0,
   55,   55,   55,   55,   55,    0,   55,   49,   49,    0,
    0,    0,    0,   49,   49,   49,   49,   55,   55,   55,
   56,   55,   55,    0,   56,   56,   56,   56,   56,    0,
   56,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   56,   56,   56,    0,   56,   56,    0,    0,   57,
    0,    0,   55,   57,   57,   57,   57,   57,    0,   57,
   50,   50,    0,    0,    0,    0,   50,   50,   50,   50,
   57,   57,   57,    0,   57,   57,   56,    0,    0,    0,
   53,    0,   53,   53,   53,    0,    0,   67,   67,    0,
    0,    0,    0,   67,   67,   67,   67,   53,   53,   53,
    0,   53,   53,    0,   54,   57,   54,   54,   54,    0,
    0,    0,    0,    0,    0,    0,    0,   61,    0,    0,
   61,   54,   54,   54,    0,   54,   54,    0,   60,   63,
   63,   60,   53,    0,   61,   61,    0,   63,   63,   61,
    0,    0,    0,    0,    0,   60,   60,    0,    0,    0,
   60,    0,    0,    0,    0,    0,   54,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   61,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   60,    0,   68,   68,    0,    0,    0,    0,   68,   68,
   68,   68,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   55,   55,    0,    0,
    0,    0,   55,   55,   55,   55,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   56,   56,    0,    0,    0,    0,   56,   56,   56,   56,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   57,
   57,    0,    0,    0,    0,   57,   57,   57,   57,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   53,   53,    0,    0,
    0,    0,   53,   53,   53,   53,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   54,   54,    0,    0,    0,    0,   54,   54,   54,   54,
    0,    0,    0,   61,   61,    0,    0,    0,    0,    0,
    0,   61,   61,    0,   60,   60,   88,   92,    0,    0,
    0,    0,   60,   60,    0,    0,  100,  101,  102,  103,
  105,    0,   88,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  131,    0,  133,    0,    0,    0,    0,    0,    0,    0,
    0,  139,    0,    0,  143,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  148,  149,  150,
  151,  152,  153,  154,    0,    0,  155,  156,  157,  158,
  159,  160,  161,    0,  162,  163,    0,    0,    0,    0,
    0,    0,   88,    0,  171,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  139,    0,  182,    0,    0,    0,    0,    0,  185,    0,
    0,    0,  187,    0,  188,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   37,   59,   59,   91,   46,   42,   40,  261,   91,   46,
   47,   45,   41,   91,   46,   44,  276,   41,   11,  125,
   41,   41,   45,  263,   17,   59,   85,  276,   41,   58,
   59,   44,   37,  123,   63,  276,   33,   42,   43,   59,
   45,   46,   47,   40,  290,   58,   59,  293,   45,   91,
   41,   59,   41,   44,   91,   44,  165,   40,  167,   91,
   53,   40,   85,   29,   93,   31,   93,   58,   59,   58,
   59,   33,   63,   39,   63,   38,   41,   40,   40,   44,
   93,   41,  191,   45,   44,   41,   91,  196,   44,  123,
   41,  125,  257,  258,  259,  260,  261,   41,   58,   59,
   44,   44,   93,   63,   93,   41,   33,  292,  293,  123,
   40,  276,   40,   40,   40,   37,   40,   40,   59,   59,
   42,   43,   40,   45,   46,   47,  123,   40,  125,   33,
   59,  190,  276,   93,   59,   40,   40,   91,   60,   59,
   62,   45,  165,   41,  167,  168,   61,   41,  276,  276,
   33,  257,  258,  259,  260,  261,   40,   40,   59,   41,
   41,  123,   45,   41,  276,   41,  268,  190,  191,   91,
   44,   33,   41,  196,  280,   41,    0,  123,   40,   59,
   41,   41,  276,   45,   59,   41,    3,   31,  276,   11,
  164,   41,   33,  276,   44,  134,  123,   -1,  276,   40,
   -1,   66,  136,   -1,   45,   -1,   -1,   -1,   58,   59,
   93,   -1,   -1,   63,   -1,   -1,   -1,   -1,  276,  276,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,   -1,
  264,  265,  266,  267,   -1,  269,  270,  271,  272,  273,
  274,  275,  276,   93,   -1,  287,  288,  281,  282,  278,
  287,  288,  276,  287,  288,  276,  290,  291,  292,  293,
  257,  258,  259,  260,  261,  262,   -1,  264,  265,  266,
  267,   -1,  269,  270,  271,  272,  273,  274,  275,   -1,
   -1,   -1,  287,  288,  281,  282,  277,  278,  277,  278,
  287,  288,   -1,   -1,  291,  257,  258,  259,  260,  261,
  262,   -1,  264,  265,  266,  267,   -1,  269,  270,  271,
  272,  273,  274,  275,   -1,   -1,   -1,  277,  278,  281,
  282,   -1,   -1,   -1,   -1,  287,  288,   -1,   -1,  291,
  257,  258,  259,  260,  261,  262,  276,  264,  265,  266,
  267,   -1,  269,  270,  271,  272,  273,  274,  275,   -1,
  290,   -1,  292,  293,  281,  282,   -1,  261,  262,   -1,
  264,  283,  284,   -1,  291,  287,  288,  271,   45,  273,
  274,  275,  257,  258,  259,  260,  261,  281,  282,  262,
   -1,  264,   -1,  287,  288,   -1,   -1,   -1,  271,   -1,
  273,  274,  275,   -1,   -1,   -1,   -1,   -1,  281,  282,
  262,   -1,  264,   -1,  287,  288,   -1,   -1,   85,  271,
   -1,  273,  274,  275,   -1,   -1,   -1,   -1,   -1,  281,
  282,  262,   -1,  264,   -1,  287,  288,  277,  278,   -1,
  271,   -1,  273,  274,  275,  285,  286,   -1,   -1,   -1,
  281,  282,   -1,   -1,   37,   -1,  287,  288,   41,   42,
   43,   44,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   58,   59,   60,   61,   62,
   63,   37,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,
   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,  165,   -1,
  167,  168,   58,   59,   60,   -1,   62,   63,   91,   -1,
   93,   37,   -1,   -1,   -1,   41,   42,   43,   -1,   45,
   46,   47,   -1,  190,  191,   -1,   -1,   -1,   -1,  196,
   -1,   -1,   -1,   59,   60,   91,   62,   63,   37,   -1,
   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   58,
   -1,   60,   37,   62,   63,   91,   41,   42,   43,   -1,
   45,   46,   47,   -1,   37,   -1,   -1,   -1,   41,   42,
   43,   -1,   45,   46,   47,   60,   -1,   62,   63,   -1,
   -1,   -1,   91,   -1,   -1,   -1,   -1,   60,   37,   62,
   63,   -1,   41,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   91,   -1,   -1,   -1,
   -1,   60,   -1,   62,   63,   37,   -1,   -1,   91,   -1,
   42,   43,   44,   45,   46,   47,   -1,   37,   -1,   -1,
   -1,   -1,   42,   43,   -1,   45,   46,   47,   60,   -1,
   62,   63,   91,   -1,   -1,   -1,   -1,   -1,   -1,   37,
   60,   -1,   62,   63,   42,   43,   -1,   45,   46,   47,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   91,
   58,   -1,   60,   -1,   62,   63,   -1,   -1,   -1,   -1,
   -1,   91,   -1,   93,  277,  278,   -1,   -1,   -1,   -1,
  283,  284,  285,  286,  287,  288,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   91,   -1,   -1,   -1,   -1,   -1,   -1,
  276,  277,  278,   -1,   -1,   -1,   -1,  283,  284,  285,
  286,  287,  288,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   37,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,   46,
   47,  277,  278,   -1,   -1,   -1,   -1,  283,  284,  285,
  286,  287,  288,   60,   -1,   62,   63,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,
   -1,   -1,   -1,   -1,  283,  284,  285,  286,  287,  288,
   -1,   -1,   -1,   -1,   91,   -1,   93,   -1,   -1,   -1,
   -1,   -1,  277,  278,   -1,   -1,   -1,   -1,  283,  284,
  285,  286,  287,  288,  277,  278,   -1,   -1,   -1,   -1,
  283,  284,  285,  286,  287,  288,   -1,   37,   -1,   -1,
   -1,   -1,   42,   43,   -1,   45,   46,   47,  277,  278,
   -1,   -1,   -1,   -1,  283,  284,  285,  286,  287,  288,
   60,   -1,   62,   63,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,   -1,
   -1,  283,  284,  285,  286,  287,  288,  277,  278,   -1,
   -1,   91,   -1,  283,  284,  285,  286,  287,  288,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,
  278,   -1,   -1,   -1,   -1,  283,  284,  285,  286,  287,
  288,   37,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,
   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   59,   60,   -1,   62,   63,   37,   -1,
   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,
   37,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,   46,
   47,   60,   -1,   62,   63,   91,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   60,   -1,   62,   -1,   -1,   -1,   -1,
  277,  278,   -1,   -1,   -1,   -1,  283,  284,  285,  286,
  287,  288,   91,   37,   -1,   -1,   -1,   -1,   42,   43,
   37,   45,   46,   47,   91,   42,   43,   -1,   45,   46,
   47,   -1,   -1,   -1,   -1,   -1,   60,   -1,   62,   -1,
   -1,   -1,   -1,   60,   -1,   62,   -1,   37,   -1,   -1,
   -1,   41,   42,   43,   44,   45,   -1,   47,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   91,   58,   59,
   60,   -1,   62,   63,   91,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,
   -1,   -1,   -1,  283,  284,  285,  286,  287,  288,   -1,
   37,   -1,   -1,   93,   41,   42,   43,   44,   45,   -1,
   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   58,   59,   60,   -1,   62,   63,   37,   -1,   -1,
   -1,   41,   42,   43,   44,   45,   -1,   47,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   58,   59,
   60,   -1,   62,   63,   -1,   -1,   93,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  277,  278,   41,   -1,   -1,   44,  283,  284,  285,
  286,  287,  288,   93,   -1,   -1,   -1,   -1,   -1,   -1,
   58,   59,   -1,   -1,   -1,   63,   -1,   -1,  277,  278,
   -1,   -1,   -1,   -1,  283,  284,  285,  286,  287,  288,
  277,  278,   -1,   -1,   -1,   -1,  283,  284,  285,  286,
  287,  288,   37,   -1,   -1,   93,   41,   42,   43,   44,
   45,   -1,   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   58,   59,   60,   -1,   62,   63,   -1,
   -1,   -1,   -1,  277,   -1,   -1,   -1,   -1,   -1,  283,
  284,  285,  286,  287,  288,   -1,  283,  284,  285,  286,
  287,  288,   -1,   -1,   -1,   -1,   37,   -1,   93,   -1,
   41,   42,   43,   44,   45,   -1,   47,  277,  278,   -1,
   -1,   -1,   -1,  283,  284,  285,  286,   58,   59,   60,
   37,   62,   63,   -1,   41,   42,   43,   44,   45,   -1,
   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   58,   59,   60,   -1,   62,   63,   -1,   -1,   37,
   -1,   -1,   93,   41,   42,   43,   44,   45,   -1,   47,
  277,  278,   -1,   -1,   -1,   -1,  283,  284,  285,  286,
   58,   59,   60,   -1,   62,   63,   93,   -1,   -1,   -1,
   41,   -1,   43,   44,   45,   -1,   -1,  277,  278,   -1,
   -1,   -1,   -1,  283,  284,  285,  286,   58,   59,   60,
   -1,   62,   63,   -1,   41,   93,   43,   44,   45,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   41,   -1,   -1,
   44,   58,   59,   60,   -1,   62,   63,   -1,   41,  277,
  278,   44,   93,   -1,   58,   59,   -1,  285,  286,   63,
   -1,   -1,   -1,   -1,   -1,   58,   59,   -1,   -1,   -1,
   63,   -1,   -1,   -1,   -1,   -1,   93,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   93,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   93,   -1,  277,  278,   -1,   -1,   -1,   -1,  283,  284,
  285,  286,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,
   -1,   -1,  283,  284,  285,  286,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  277,  278,   -1,   -1,   -1,   -1,  283,  284,  285,  286,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,
  278,   -1,   -1,   -1,   -1,  283,  284,  285,  286,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,
   -1,   -1,  283,  284,  285,  286,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  277,  278,   -1,   -1,   -1,   -1,  283,  284,  285,  286,
   -1,   -1,   -1,  277,  278,   -1,   -1,   -1,   -1,   -1,
   -1,  285,  286,   -1,  277,  278,   50,   51,   -1,   -1,
   -1,   -1,  285,  286,   -1,   -1,   60,   61,   62,   63,
   64,   -1,   66,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   84,   -1,   86,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   95,   -1,   -1,   98,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  111,  112,  113,
  114,  115,  116,  117,   -1,   -1,  120,  121,  122,  123,
  124,  125,  126,   -1,  128,  129,   -1,   -1,   -1,   -1,
   -1,   -1,  136,   -1,  138,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  164,   -1,  166,   -1,   -1,   -1,   -1,   -1,  172,   -1,
   -1,   -1,  176,   -1,  178,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=293;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'","'?'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"VOID","BOOL","INT","STRING",
"CLASS","NULL","EXTENDS","THIS","WHILE","FOR","IF","ELSE","RETURN","BREAK",
"NEW","PRINT","READ_INTEGER","READ_LINE","LITERAL","IDENTIFIER","AND","OR",
"UMINUS","STATIC","INSTANCEOF","NUMINSTANCES","LESS_EQUAL","GREATER_EQUAL",
"EQUAL","NOT_EQUAL","SELF_PLUS","SELF_MINUS","EMPTY","FI","DO","OD","GUARD",
};
final static String yyrule[] = {
"$accept : Program",
"Program : ClassList",
"ClassList : ClassList ClassDef",
"ClassList : ClassDef",
"VariableDef : Variable ';'",
"Variable : Type IDENTIFIER",
"Type : INT",
"Type : VOID",
"Type : BOOL",
"Type : STRING",
"Type : CLASS IDENTIFIER",
"Type : Type '[' ']'",
"ClassDef : CLASS IDENTIFIER ExtendsClause '{' FieldList '}'",
"ExtendsClause : EXTENDS IDENTIFIER",
"ExtendsClause :",
"FieldList : FieldList VariableDef",
"FieldList : FieldList FunctionDef",
"FieldList :",
"Formals : VariableList",
"Formals :",
"VariableList : VariableList ',' Variable",
"VariableList : Variable",
"FunctionDef : STATIC Type IDENTIFIER '(' Formals ')' StmtBlock",
"FunctionDef : Type IDENTIFIER '(' Formals ')' StmtBlock",
"StmtBlock : '{' StmtList '}'",
"StmtList : StmtList Stmt",
"StmtList :",
"Stmt : VariableDef",
"Stmt : SimpleStmt ';'",
"Stmt : IfStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"Stmt : ReturnStmt ';'",
"Stmt : PrintStmt ';'",
"Stmt : BreakStmt ';'",
"Stmt : StmtBlock",
"Stmt : GuardedIfStmt",
"Stmt : GuardedDoStmt",
"SimpleStmt : LValue '=' Expr",
"SimpleStmt : Call",
"SimpleStmt :",
"Receiver : Expr '.'",
"Receiver :",
"LValue : Receiver IDENTIFIER",
"LValue : Expr '[' Expr ']'",
"Call : Receiver IDENTIFIER '(' Actuals ')'",
"Expr : LValue",
"Expr : Call",
"Expr : Constant",
"Expr : SELF_PLUS Expr",
"Expr : SELF_MINUS Expr",
"Expr : Expr SELF_PLUS",
"Expr : Expr SELF_MINUS",
"Expr : Expr '+' Expr",
"Expr : Expr '-' Expr",
"Expr : Expr '*' Expr",
"Expr : Expr '/' Expr",
"Expr : Expr '%' Expr",
"Expr : Expr EQUAL Expr",
"Expr : Expr NOT_EQUAL Expr",
"Expr : Expr '<' Expr",
"Expr : Expr '>' Expr",
"Expr : Expr LESS_EQUAL Expr",
"Expr : Expr GREATER_EQUAL Expr",
"Expr : Expr AND Expr",
"Expr : Expr OR Expr",
"Expr : '(' Expr ')'",
"Expr : '-' Expr",
"Expr : '!' Expr",
"Expr : Expr '?' Expr ':' Expr",
"Expr : READ_INTEGER '(' ')'",
"Expr : READ_LINE '(' ')'",
"Expr : THIS",
"Expr : NEW IDENTIFIER '(' ')'",
"Expr : NEW Type '[' Expr ']'",
"Expr : INSTANCEOF '(' Expr ',' IDENTIFIER ')'",
"Expr : NUMINSTANCES '(' IDENTIFIER ')'",
"Expr : '(' CLASS IDENTIFIER ')' Expr",
"Constant : LITERAL",
"Constant : NULL",
"Actuals : ExprList",
"Actuals :",
"ExprList : ExprList ',' Expr",
"ExprList : Expr",
"WhileStmt : WHILE '(' Expr ')' Stmt",
"ForStmt : FOR '(' SimpleStmt ';' Expr ';' SimpleStmt ')' Stmt",
"BreakStmt : BREAK",
"IfStmt : IF '(' Expr ')' Stmt ElseClause",
"ElseClause : ELSE Stmt",
"ElseClause :",
"ReturnStmt : RETURN Expr",
"ReturnStmt : RETURN",
"PrintStmt : PRINT '(' ExprList ')'",
"GuardedIfStmt : IF GuardedStmts FI",
"GuardedDoStmt : DO GuardedStmts OD",
"GuardedStmts : GuardedStmts GUARD GuardedStmt",
"GuardedStmts : GuardedStmt",
"GuardedStmt : Expr ':' StmtList",
};

//#line 481 "Parser.y"
    
    /**
     * 打印当前归约所用的语法规则<br>
     * 请勿修改。
     */
    public boolean onReduce(String rule) {
        if (rule.startsWith("$$"))
            return false;
        else
            rule = rule.replaceAll(" \\$\\$\\d+", "");

   	    if (rule.endsWith(":"))
    	    System.out.println(rule + " <empty>");
   	    else
			System.out.println(rule);
		return false;
    }
    
    public void diagnose() {
		addReduceListener(this);
		yyparse();
	}
//#line 756 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    //if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      //if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        //if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        //if (yychar < 0)    //it it didn't work/error
        //  {
        //  yychar = 0;      //change it to default string (no -1!)
          //if (yydebug)
          //  yylexdebug(yystate,yychar);
        //  }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        //if (yydebug)
          //debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      //if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0 || valptr<0)   //check for under & overflow here
            {
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            //if (yydebug)
              //debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            //if (yydebug)
              //debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0 || valptr<0)   //check for under & overflow here
              {
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        //if (yydebug)
          //{
          //yys = null;
          //if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          //if (yys == null) yys = "illegal-symbol";
          //debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          //}
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    //if (yydebug)
      //debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    if (reduceListener == null || reduceListener.onReduce(yyrule[yyn])) // if intercepted!
      switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 56 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 62 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 66 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 76 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 82 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 86 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 90 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 94 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 98 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 11:
//#line 102 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 12:
//#line 108 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 13:
//#line 114 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 14:
//#line 118 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 15:
//#line 124 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 16:
//#line 128 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 17:
//#line 132 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 19:
//#line 140 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 20:
//#line 147 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 21:
//#line 151 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 22:
//#line 158 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 23:
//#line 162 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 168 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 25:
//#line 174 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 26:
//#line 178 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 27:
//#line 185 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 28:
//#line 190 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 38:
//#line 207 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 39:
//#line 211 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 40:
//#line 215 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 42:
//#line 222 "Parser.y"
{
                        yyval = new SemValue();
                    }
break;
case 43:
//#line 228 "Parser.y"
{
                        yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
                        if (val_peek(1).loc == null) {
                            yyval.loc = val_peek(0).loc;
                        }
                    }
break;
case 44:
//#line 235 "Parser.y"
{
                        yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                    }
break;
case 45:
//#line 241 "Parser.y"
{
                        yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
                        if (val_peek(4).loc == null) {
                            yyval.loc = val_peek(3).loc;
                        }
                    }
break;
case 46:
//#line 250 "Parser.y"
{
                        yyval.expr = val_peek(0).lvalue;
                    }
break;
case 49:
//#line 256 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.PREINC, val_peek(0).expr, val_peek(0).loc);
                    }
break;
case 50:
//#line 260 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.PREDEC, val_peek(0).expr, val_peek(0).loc);
                    }
break;
case 51:
//#line 264 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.POSTINC, val_peek(1).expr, val_peek(1).loc);
                    }
break;
case 52:
//#line 268 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.POSTDEC, val_peek(1).expr, val_peek(1).loc);
                    }
break;
case 53:
//#line 272 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 54:
//#line 276 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 55:
//#line 280 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 56:
//#line 284 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 57:
//#line 288 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 58:
//#line 292 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 59:
//#line 296 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 60:
//#line 300 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 61:
//#line 304 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 62:
//#line 308 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 63:
//#line 312 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 64:
//#line 316 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 65:
//#line 320 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 66:
//#line 324 "Parser.y"
{
                        yyval = val_peek(1);
                    }
break;
case 67:
//#line 328 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 68:
//#line 332 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 69:
//#line 336 "Parser.y"
{
                        yyval.expr = new Tree.Ternary(Tree.TERNARY, val_peek(4).expr, val_peek(2).expr, val_peek(0).expr, val_peek(4).loc);
                    }
break;
case 70:
//#line 340 "Parser.y"
{
                        yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                    }
break;
case 71:
//#line 344 "Parser.y"
{
                        yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                    }
break;
case 72:
//#line 348 "Parser.y"
{
                        yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                    }
break;
case 73:
//#line 352 "Parser.y"
{
                        yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                    }
break;
case 74:
//#line 356 "Parser.y"
{
                        yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                    }
break;
case 75:
//#line 360 "Parser.y"
{
                        yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                    }
break;
case 76:
//#line 364 "Parser.y"
{
                        yyval.expr = new Tree.Numinstances(val_peek(1).ident, val_peek(3).loc);
                    }
break;
case 77:
//#line 368 "Parser.y"
{
                        yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                    }
break;
case 78:
//#line 374 "Parser.y"
{
                        yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
                    }
break;
case 79:
//#line 378 "Parser.y"
{
                        yyval.expr = new Null(val_peek(0).loc);
                    }
break;
case 81:
//#line 385 "Parser.y"
{
                        yyval = new SemValue();
                        yyval.elist = new ArrayList<Tree.Expr>();
                    }
break;
case 82:
//#line 392 "Parser.y"
{
                        yyval.elist.add(val_peek(0).expr);
                    }
break;
case 83:
//#line 396 "Parser.y"
{
                        yyval.elist = new ArrayList<Tree.Expr>();
                        yyval.elist.add(val_peek(0).expr);
                    }
break;
case 84:
//#line 403 "Parser.y"
{
                        yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
                    }
break;
case 85:
//#line 409 "Parser.y"
{
                        yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
                    }
break;
case 86:
//#line 415 "Parser.y"
{
                        yyval.stmt = new Tree.Break(val_peek(0).loc);
                    }
break;
case 87:
//#line 421 "Parser.y"
{
                        yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
                    }
break;
case 88:
//#line 427 "Parser.y"
{
                        yyval.stmt = val_peek(0).stmt;
                    }
break;
case 89:
//#line 431 "Parser.y"
{
                        yyval = new SemValue();
                    }
break;
case 90:
//#line 437 "Parser.y"
{
                        yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 91:
//#line 441 "Parser.y"
{
                        yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                    }
break;
case 92:
//#line 447 "Parser.y"
{
                        yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
                    }
break;
case 93:
//#line 452 "Parser.y"
{
                        yyval.stmt = new Tree.GuardedIf(val_peek(1).glist, val_peek(1).loc);
                    }
break;
case 94:
//#line 457 "Parser.y"
{
                        yyval.stmt = new Tree.GuardedDo(val_peek(1).glist, val_peek(1).loc);
                    }
break;
case 95:
//#line 462 "Parser.y"
{
                        yyval.glist.add(val_peek(0).gdst);
                    }
break;
case 96:
//#line 467 "Parser.y"
{
                        yyval = new SemValue();
                        yyval.glist = new ArrayList<Tree.GuardedStmt>();
                        yyval.glist.add(val_peek(0).gdst);
                    }
break;
case 97:
//#line 475 "Parser.y"
{
                        yyval.gdst = new Tree.GuardedStmt(val_peek(2).expr, val_peek(0).slist, val_peek(2).loc);
                    }
break;
//#line 1411 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    //if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      //if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        //if (yychar<0) yychar=0;  //clean, if necessary
        //if (yydebug)
          //yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      //if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
