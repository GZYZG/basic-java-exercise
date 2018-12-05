package calculator;

import java.util.*;

public class calexpress{
	static Map<String, Integer> op = new HashMap<String,Integer>();
	static Scanner input = new Scanner(System.in);
	static Set<String> correctchrset = new HashSet<String>();
	static String[] correctchrs = {"7","8","9","/","(",")",
		     "4","5","6","x","^","!","1","2","3","+", "0",".","-"};
	
	public static void initCollection() {
		//将运算符和相应的优先级添加入字典中，数字越小，优先级越高
		op.put(")",0);
		op.put("(", 1);
		op.put("!", 2);
		op.put("^", 2);
		op.put("x", 3);
		op.put("/", 3);
		op.put("+", 4);
		op.put("-", 4);
		
		//将运算符和数字加入到集合中
		for(int i = 0;i < correctchrs.length; i++) {
			correctchrset.add(correctchrs[i]);
		}
	}
	//比较op1和op2的优先级，若op1优先于op2则返回true，否则返回false
	public static boolean cmpPriority(String op1,String op2) {
		if(op.get(op1) < op.get(op2)) {
			return true;
		}
		return false;
		
	}
	//进行计算
	public static String getFloat(String expr,int start) {
		int index = start;
		boolean flag = false;
		char c;
		for(index = start; index < expr.length(); index++) {
			c = expr.charAt(index);
			if(c == '-' || c == '+' || c == 'x' || c == '/' || c == '^' || c == ')' || c == '!') {
				break;
			}		
		}
		
		return (expr.substring(start, index));
	}
	
	//两个操作数和一个运算符进行运算
	public static float calculate(float num1,float num2,String op) {
		switch(op) {
		case "+":return (num1+num2);
		case "-":return (num1-num2);
		case "x":return (num1*num2);
		case "/":return (num1/num2);
		case "^":return ((float) Math.pow(num1, num2));
		default:return -404040404;
		}
	}
	
	//将两个操作数和一个运算符弹出进行运算并将结果压入栈
	public static float getNumsOp2Cal(ArrayList<String> op,int pop,ArrayList<Float> num,int pnum) {
		//弹出一个操作符
		String operator = op.remove(pop);
		pop--;
		//弹出两个操作数
		float num1 = num.remove(pnum);
		pnum--;
		float num2 = num.remove(pnum);
		pnum--;
		if((num2 == 0f && operator == "/") || (num1 < 0 && operator == "^")) {
			System.out.println("除零错误！！！");
			return -404040404;
		}
		//计算结果，将结果入栈
		float result = calculate(num2,num1,operator);
		pnum++;
		num.add(pnum,result);	
		return result;
	}
	
	//计算阶乘
	public static int factorial(int n) {
		if(n == 1) {
			return 1;
		}else {
			return factorial(n-1)*n;
		}
	}
	
	public static float calculate(String express) {
		if(express.isEmpty()) {
			return -404040404;
		}
		initCollection();
		//构建两个栈，用于存放操作数和操作符
		int pnumstack = -1;
		int popstack = -1;
		ArrayList<String> opstack = new ArrayList<String>(30);
		ArrayList<Float> numstack = new ArrayList<Float>(50);
		//表达式
		//String express = "";
		//express = input.next();
		
		//对表达式进行求解
		int count = 0;
		int len = express.length();
		//float num1 = 0.0f,num2 = 0.0f,result = 0f;
		//String operator = "";
		String tmp = "";
		while(count < len) {
			//逐个读取字符串表达式中的字符，并转换为字符串
			tmp = String.valueOf(express.charAt(count));
			
			//如果表达式中存在非法字符则退出！
			if(!correctchrset.contains(tmp)) {
				System.out.println("表达式中存在非法字符！请重新输入！");
				return -404040404;
			}
			//如果是数字
			if(Character.isDigit(tmp.charAt(0)) || tmp == ".") {
				//System.out.println("in  if");
				tmp = getFloat(express,count);
				pnumstack++;
				numstack.add(pnumstack,Float.parseFloat(tmp));	
				count += tmp.length();
				continue;
			}

			//如果是运算符，则需要进行判断是否需要进行运算，标准是当前运算符比栈顶运算符优先级低
			if(!Character.isDigit(tmp.charAt(0))) {
				//在将运算符添加进栈前，先比较当前的运算符和栈顶的运算符				
				if(popstack == -1 || opstack.get(popstack).equals("(") || ( cmpPriority(tmp,opstack.get(popstack)) && (!tmp.equals(")")) ) ) {
					//若栈为空，或者tmp是"("，或者tmp优先级高于栈顶运算符优先级且tmp不是")"，则直接入栈
					//若tmp优先级高于栈顶运算符优先级且tmp是")"，则不入栈，而是对这个右括号包括的表达式进行计算
					popstack++;
					opstack.add(popstack,tmp);
				}else if(!cmpPriority(tmp,opstack.get(popstack)) || tmp.equals(")") ) {					
					//若当前运算符的优先级更低则需要弹出栈顶运算符以及两个操作数进行运算
					//如果当前的运算符是)则需要一直运算至运算符栈的栈顶是(
					if(tmp.equals(")") ) {
						while(popstack >= 0) {
							//若操作数栈中操作数少于两个或者运算符栈为空则返回错误码-404040404
							if(popstack == -1 || pnumstack < 0) {
								return -404040404;
							}
							if(opstack.get(popstack).equals("!") && pnumstack >= 0) {
								opstack.remove(popstack);
								popstack--;
								int num = Math.round(numstack.remove(pnumstack));
								if(num < 0) {
									return -404040404;
								}
								float result = factorial(num);
								pnumstack++;
								numstack.add(pnumstack,result);
								
								continue;
							}
							getNumsOp2Cal(opstack,popstack,numstack,pnumstack);
							popstack--;
							pnumstack--;
							pnumstack--;
							pnumstack++;
							if(popstack >= 0 && opstack.get(popstack).equals("(") ) {
								//若遇到(而运算结束，则将(丢弃
								opstack.remove(popstack);
								popstack--;
								if(count == len-1 ) {
									continue;
								}
								break;
							}else if(popstack < 0 && count != len-1) {
								//若运算到最后也没遇到(，则出错，左括号不匹配！
								System.out.println("左括号不匹配！");
								return -404040404;
								//System.exit(-1);
							}
						}	
					}else {
						//一般情况只需要弹出运算符和操作数直至当前字符串的优先级比栈顶运算符优先级高或者运算符栈为空
						while( popstack >= 0 && !opstack.get(popstack).equals("(") && !cmpPriority(tmp,opstack.get(popstack)) ){						
							//若操作数栈或者运算符栈为空则返回错误码-404040404
							if(popstack == -1 || pnumstack < 0) {
								return -404040404;
							}
							if(opstack.get(popstack).equals("!") && pnumstack >= 0) {
								opstack.remove(popstack);
								popstack--;
								int num = Math.round(numstack.remove(pnumstack));
								if(num < 0) {
									return -404040404;
								}
								float result = factorial(num);
								pnumstack++;
								numstack.add(pnumstack,result);
								
								continue;
							}
							getNumsOp2Cal(opstack,popstack,numstack,pnumstack);
							popstack--;
							pnumstack--;
							pnumstack--;
							pnumstack++;						
						}
						//计算完毕后需要将tmp入栈
						popstack++;
						opstack.add(popstack,tmp);
					}				
				}								
			}
			count++;
		}

		while(popstack != -1) {			
			//若操作数栈或者运算符栈为空则返回错误码-404040404
			if(popstack == -1 || pnumstack < 0) {
				return -404040404;
			}
			if(opstack.get(popstack).equals("!") && pnumstack >= 0) {
				opstack.remove(popstack);
				popstack--;
				int num = Math.round(numstack.remove(pnumstack));
				pnumstack--;
				if(num < 0) {
					return -404040404;
				}
				float result = factorial(num);
				pnumstack++;
				numstack.add(pnumstack,result);
				
				continue;
			}
			getNumsOp2Cal(opstack,popstack,numstack,pnumstack);
			popstack--;
			pnumstack--;
			pnumstack--;
			pnumstack++;
		}
		//test:1+2+3+(2+2)^3+0.5x1+(1+1.5)x(1+1)	
		if(popstack != -1) {
			System.out.println("运算符输入错误！"+"\n"+opstack.toString());		
			return -404040404;
			//System.exit(-1);
		}
		return numstack.get(0);
		//System.out.println(express+" = "+numstack.get(0));
	}
}