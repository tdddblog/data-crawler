package ek.html;

%%

%public
%class HrefLexer

%integer
%function getNextToken

%unicode

%line
%column

%{
public static final int HREF = 1;

StringBuilder string = new StringBuilder();

public String getString() {
    return string.toString();
}

%}

LineTerminator = \r|\n|\r\n
HrefChar1 = [^\r\n\">]
HrefChar2 = [^\r\n\'>]

WhiteSpace = {LineTerminator} | [ \t\f]


AStart = "<" [aA] {WhiteSpace}+
HrefBase = [hH] [rR] [eE] [fF] {WhiteSpace}* "=" {WhiteSpace}* 
Href1 = {HrefBase} "\""
Href2 = {HrefBase} "\'"

%state A_STATE
%state STRING_STATE1
%state STRING_STATE2

%%

<YYINITIAL> {
  {AStart}              { yybegin(A_STATE);}
}

<A_STATE> {
  {Href1}               { yybegin(STRING_STATE1); string.setLength(0); }
  {Href2}               { yybegin(STRING_STATE2); string.setLength(0); }
}

<STRING_STATE1> {
  \"                    { yybegin(YYINITIAL); return HREF; }
  {HrefChar1}+          { string.append(yytext()); }
  ">"                   { throw new RuntimeException("Unterminated href at column " + yycolumn+1); }
  {LineTerminator}      { throw new RuntimeException("Unterminated href at end of line " + yyline+1); }
}

<STRING_STATE2> {
  \'                    { yybegin(YYINITIAL); return HREF; }
  {HrefChar2}+          { string.append(yytext()); }
  ">"                   { throw new RuntimeException("Unterminated href at column " + yycolumn+1); }
  {LineTerminator}      { throw new RuntimeException("Unterminated href at end of line " + yyline+1); }
}


[^]                     {}
