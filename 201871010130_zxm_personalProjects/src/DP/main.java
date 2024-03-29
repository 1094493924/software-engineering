package DP;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.Scanner;

import javax.swing.*;
import sun.tools.jar.Main;
import java.lang.reflect.Field;

/**
 * 
 * @author 201871010130_周学铭
 * 折扣背包其本质就是特殊的分组背包，所有下标都从1开始。
 * 
 */

public class main  extends JPanel{
	static int Num = 0,Weight=0;				//Num:背包组数，Weight:总重量
	static Scanner input=new Scanner(System.in);
	static int weight[][];						//数据集中读入的重量，第一维表示第几组，第二维表示该组的第一个元素
	static int value[][];						//数据集中读入的价格
	static int row=1,col=1;						//数据集分组背包个数，及每个背包的物品个数
	static int dp[];							//dp求解
	static int goods[][];						//回溯法求解
	static int res;								//最优解
	static int back_weight,back_value;			//回溯法记录部分最优解的临时变量
	static double run_time;						//程序运行时间
	static long back_count;						//回溯法执行次数，用来判断回溯法能否在有效时间内运行结束
	static int ans[];
	static int remain_capacity;
	
	//读取数据集有效数据
	public static void read_file_data(String file_name) {
		try {
			int t;
            BufferedReader in = new BufferedReader(new FileReader(file_name));
            if(file_name=="data_set\\idkp1-10.txt") {
            	System.out.println("请选择读取一组数据(0-10):");
            }
            else {
            	System.out.println("请选择读取一组数据(1-10):");
            }
            System.out.print("请输入:");
            t=input.nextInt();
            if(t>10||t<0) {
            	System.out.println("没有该组数据");
            	return;
            }
            else {
            	String str;
	            while ((str = in.readLine()) != null) {
	            	if((str.length()==6&&str.charAt(4)==t+'0')||(str.length()==7&&str.charAt(4)=='1'&&str.charAt(5)=='0'&&t==10)) {
	                	int x=0;
	                	String str1 = in.readLine();
	                	while(str1=="")
	                		str1 = in.readLine();
	                	int i=0;
	                	//清除垃圾数据
	                	for(;i<str1.length();i++) {
	                		char ch = str1.charAt(i);
	                		if(ch>='0'&&ch<='9') {
	                			break;
	                		}
	                	}
	                	//读取每组背包的个数
	                	for(;i<str1.length();i++) {
	                		char ch = str1.charAt(i);
	                		if(ch>='0'&&ch<='9') {
	                			x=x*10+ch-'0';
	                		}
	                		else {
	                			break;
	                		}
	                	}
	                	//清除垃圾数据
	                	for(;i<str1.length();i++) {
	                		char ch = str1.charAt(i);
	                		if(ch>='0'&&ch<='9') {
	                			break;
	                		}
	                	}
	                	//读取有几组背包
	                	for(;i<str1.length();i++) {
	                		char ch = str1.charAt(i);
	                		if(ch>='0'&&ch<='9') {
	                			Num= Num*10+ch-'0';
	                		}
	                		else {
	                			break;
	                		}
	                	}
	                	//清除垃圾数据
	                	for(;i<str1.length();i++) {
	                		char ch = str1.charAt(i);
	                		if(ch>='0'&&ch<='9') {
	                			break;
	                		}
	                	}
	                	//读取背包容量
	                	for(;i<str1.length();i++) {
	                		char ch = str1.charAt(i);
	                		if(ch>='0'&&ch<='9') {
	                			Weight=Weight*10+ch-'0';
	                		}
	                		else {
	                			break;
	                		}
	                	}
	                	//读取背包价值
	                	String profit;
	                	value=new int[10000][5];
	                	int temp_num=0;
	                	profit=in.readLine();
	                	while(profit.isEmpty())
	                		profit=in.readLine();
	                	profit=in.readLine();
	                	while(profit.isEmpty())
	                		profit=in.readLine();
	                	for(i=0;i<profit.length();i++) {
	                		char ch = profit.charAt(i);
	                		if(ch>='0'&&ch<='9') {
	                			temp_num=temp_num*10+ch-'0';
	                		}
	                		else {
	                			value[row][col++]=temp_num;
	                			if(col>3) {
	                				row++;col=1;
	                			}
	                			temp_num=0;
	                		}
	                	}
	                	//读取背包容量
	                	String Weight1;
	                	weight=new int[10000][5];
	                	row=1;col=1;
	                	temp_num=0;
	                	Weight1=in.readLine();
	                	while(Weight1.isEmpty())
	                		Weight1=in.readLine();
	                	Weight1=in.readLine();
	                	while(Weight1.isEmpty())
	                		Weight1=in.readLine();
	                	for(i=0;i<Weight1.length();i++) {
	                		char ch = Weight1.charAt(i);
	                		if(ch>='0'&&ch<='9') {
	                			temp_num=temp_num*10+ch-'0';
	                		}
	                		else {
	                			weight[row][col++]=temp_num;
	                			if(col>3) {
	                				row++;col=1;
	                			}
	                			temp_num=0;
	                		}
	                	}
	                }
	            }
            }
        } catch (IOException e) {
        	System.out.printf("没有该文件");
        }
	}
	
	
	//选取数据集
	public static void read_file() {
		int _;
		System.out.println("请选择读取一个测试数据集:");
		System.out.println("1:idkp1-10.txt\t2:sdkp1-10.txt\t3:udkp1-10.txt\t4:wdkp1-10.txt\t其他:终止程序");
		System.out.print("请输入:");
		_=input.nextInt();
		switch(_) {
		case 1:
			read_file_data("data_set\\idkp1-10.txt");
			break;
		case 2:
			read_file_data("data_set\\sdkp1-10.txt");
			break;
		case 3:
			read_file_data("data_set\\udkp1-10.txt");
			break;
		case 4:
			read_file_data("data_set\\wdkp1-10.txt");
			break;
		default:System.out.println("程序运行结束!");return;
		}
	}
	
	
	//绘制散点图
	static int data[][];
	final int PAD =20;
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        // Draw ordinate.
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h-PAD));
        // Draw abcissa.
        g2.draw(new Line2D.Double(PAD, h-PAD, w-PAD, h-PAD));
        double xInc = (double)(w - 2*PAD)/getWeightMax();
        double scale = (double)(h - 2*PAD)/getValueMax();
        // Mark data points.
        g2.setPaint(Color.red);
        for(int i = 0; i < data[0].length; i++) {
            double x = PAD + xInc*data[0][i];
            double y = h - PAD - scale*data[1][i];
            g2.fill(new Ellipse2D.Double(x-2, y-2, 4, 4));
        }
    }

    private int getWeightMax() {
        int max = -Integer.MAX_VALUE;
        for(int i = 0; i < data[0].length; i++) {
            if(data[0][i] > max)
                max = data[0][i];
        }
        return max;
    }
    
    private int getValueMax() {
        int max = -Integer.MAX_VALUE;
        for(int i = 0; i < data[1].length; i++) {
            if(data[1][i] > max)
                max = data[1][i];
        }
        return max;
    }
	
    public static void draw_scatterplot() {
    	int temp_num=(row+1)*3;
    	data=new int[2][temp_num];
    	for(int i=1;i<row;i++) {
    		for(int j=1;j<=3;j++) {
    			data[0][(i-1)*3+j]=weight[i][j];
    			data[1][(i-1)*3+j]=value[i][j];
    		}
    	}
    	 JFrame f = new JFrame();
         f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         f.add(new main());
         f.setSize(300,300);
         f.setLocation(200,200);
         f.setVisible(true);
    }
    
    //数据排序
    public static void data_sort() {
    	for(int i=1;i<row;i++) {
    		for(int j=i+1;j<row;j++) {
    			double x=value[i][3]/(double)weight[i][3];
    			double y=value[j][3]/(double)weight[j][3];
    			if(x<y) {
    				int a;
    				a=value[i][1];
    				value[i][1]=value[j][1];
    				value[j][1]=a;
    				a=value[i][2];
    				value[i][2]=value[j][2];
    				value[j][2]=a;
    				a=value[i][3];
    				value[i][3]=value[j][3];
    				value[j][3]=a;
    				
    				a=weight[i][1];
    				weight[i][1]=weight[j][1];
    				weight[j][1]=a;
    				a=weight[i][2];
    				weight[i][2]=weight[j][2];
    				weight[j][2]=a;
    				a=weight[i][3];
    				weight[i][3]=weight[j][3];
    				weight[j][3]=a;
    			}
    		}
    	}
    	for(int i=1;i<row;i++) {
    		System.out.println("第"+i+"组数据:"+"("+value[i][1]+","+weight[i][1]+")\t("+value[i][2]+","+weight[i][2]+")\t("+value[i][3]+","+weight[i][3]+")\t(性价比:"+value[i][3]/(double)weight[i][3]+")");
    	}
    }	
    
    //动态规划法求解
    public static void DP(){
    	dp=new int[1000000];
    	for(int i=1;i<row;i++) {
    		for(int j=Weight;j>=0;j--) {
    			for(int k=1;k<=3;k++) {
    				if(weight[i][k]>j) {
    					continue;
    				}
    				if(dp[j]<dp[j-weight[i][k]]+value[i][k]) {
    					dp[j]=dp[j-weight[i][k]]+value[i][k];
    				}
    			}
    		}
    	}
    	for(int i=Weight-1;i>=0;i--) {
    		if(dp[i]==dp[i+1]) {
    			remain_capacity++;
    		}
    		else {
    			break;
    		}
    	}
    	res=dp[Weight];
    }
     
    //回溯法求解
    public static void dfs(int x){
    	back_count++;
    	if(back_count>100000000) {
    		return;
    	}
    	if(x+1>=row) {
    		return ;
    	}	
    	else {
    		if(weight[x+1][3]<=back_weight) {
    			back_weight-=weight[x+1][3];
    			back_value+=value[x+1][3];
    			if(res<back_value) {
    				res=back_value;
    			}
    			dfs(x+1);
    			back_weight+=weight[x+1][3];
    			back_value-=value[x+1][3];
    		}
			dfs(x+1);
			if(weight[x+1][1]<=back_weight) {
    			back_weight-=weight[x+1][1];
    			back_value+=value[x+1][1];
    			dfs(x+1);
    			if(res<back_value) {
    				res=back_value;
    			}
    			back_weight+=weight[x+1][1];
    			back_value-=value[x+1][1];
			}
			if(weight[x+1][2]<=back_weight) {	
    			back_weight-=weight[x+1][2];
    			back_value+=value[x+1][2];
    			if(res<back_value) {
    				res=back_value;
    			}
    			dfs(x+1);
    			back_weight+=weight[x+1][2];
    			back_value-=value[x+1][2];
			}
    	}
    }
    
    //自主选择方法求解
    public static void solve() {
    	System.out.println("请选择一种方法来求解D{0-1}问题");
    	System.out.println("1:动态规划法\t2:回溯法");
    	System.out.print("请选择:");
    	int t;
    	t=input.nextInt();
    	if(t!=1&&t!=2) {
    		System.out.println("该选项不存在!!");
    		solve();
    	}
    	if(t==1) {
    		long  startTime = System.currentTimeMillis(); 
    		DP();
    		long endTime = System.currentTimeMillis();
    		run_time=(endTime - startTime)/1000.0;
    		System.out.println("最优解:"+res);
    		System.out.println("运行时间:"+run_time+"s");
    	}
    	else if(t==2) {
    		back_weight=Weight;
    		back_value=0;
    		long  startTime = System.currentTimeMillis(); 
    		ans=new int[1010];
    		dfs(0);
    		if(back_count>100000000) {
    			System.out.println("数据过大,回溯法无法在短时间内得到结果");
    			return;
    		}
    		long endTime = System.currentTimeMillis();
    		run_time=(endTime - startTime)/1000.0;
    		System.out.println("最优解:"+res);
    		System.out.println("运行时间:"+run_time+"s");
    	}
    }
    
    //将答案写入txt文件
    static void write_to_txt() throws FileNotFoundException {
    	PrintStream ps = new PrintStream("res.txt");
    	ps.println("最优解:"+res);
    	ps.println("运行时间:"+run_time+"s");
    	ps.println("求得最优解后背包的剩余容量:"+remain_capacity);
    	ps.close();
    }
    
    static void get_remain_capacity() {
    	System.out.println("求得最优解后背包的剩余容量:"+remain_capacity);
    }
    
    //主函数
	public static void main(String[] args) throws FileNotFoundException {
		read_file();			//读取文本有效数据
		draw_scatterplot();		//绘制散点图
		data_sort();			//数据排序
		solve();				//选择动态规划或者回溯法求解
		write_to_txt();			//存储结果到txt
		get_remain_capacity();	//获取得到最优解后，背包剩余的容量
		System.out.println("程序运行结束！！！");
	}
}
