package utilities;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.opencsv.CSVWriter;

public class Tests extends Thread{
	
	ArrayList<User> data;
	int index;
	public double[] accuracy = new double[9];
	public ArrayList<String[]> log = new ArrayList<String[]>();
	
	public Tests(int index, ArrayList<User> data) {
		this.index = index;
		this.data = data;
	}
	
	public String arrayToString(ArrayList<String> arr) {
		String s = "{";
		for(int i=0;i<arr.size();i++) {
			s += arr.get(i);
			if(i<arr.size()-1) {
				s+=",";
			}
		}
		s+="}";
		return s;
	}
	
	public String arrayTemplateToString(ArrayList<Template> arr) {
		String s = "{";
		for(int i=0;i<arr.size();i++) {
			s += arr.get(i).getTypeEnum()+"-"+arr.get(i).getID();
			if(i<arr.size()-1) {
				s+=",";
			}
		}
		s+="}";
		return s;
	}
	
	@Override
	public void run() {
		System.out.println("Thread "+index+" started.");
		if(index<data.size() && index>=0) {
			HashMap<String, ArrayList<Template>> d = data.get(index).getGestures();
			
			Random r = new Random();
			for(int T=1; T<=9; T++) {
				double correct = 0;
				double total = 0;
				for(int i=0;i<100;i++) {
					ArrayList<Template> trainingSet = new ArrayList<Template>();
					ArrayList<Template> testSet = new ArrayList<Template>();
					for(String type: d.keySet()) {
						@SuppressWarnings("unchecked")
						ArrayList<Template> templates = (ArrayList<Template>) d.get(type).clone();
						for(int tsize = 0; tsize<T; tsize++) {
							int rindex = r.nextInt(templates.size());
							trainingSet.add(templates.get(rindex));
							
							templates.remove(rindex);
						}
						testSet.add(templates.get(r.nextInt(templates.size())));
					}
					for(Template c: testSet) {
						Result result = DollarP.recognize(c, trainingSet);
						String crct = "0";
						if(result.getTemp().getType().equals(c.getType())){
							correct++;
							crct = "1";
						}
						total++;
						String[] s = new String[] {"p"+data.get(index).getUserID(), c.getTypeEnum()+"", ""+(i+1), 
								""+T, trainingSet.size()+"", arrayTemplateToString(trainingSet), c.getTypeEnum()+"-"+c.getID(), 
								result.getTemp().getTypeEnum()+"", crct, String.format("%.2f", result.getScore())+"", result.getStr().get(0), arrayToString(result.getStr())};
						log.add(s);
					}
					//System.out.println("T:"+T+"  iteration:"+i);
				}
				accuracy[T-1] = correct/total;
				System.out.println("Thread "+index+": "+T*10+"% complete!" );
			}
			
//			for(int i =0;i<9;i++) {
//				System.out.println((i+1)+":"+accuracy[i]);
//			}
			
		}
		System.out.println("Thread "+ index + " Done!");
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
		LocalDateTime started = LocalDateTime.now();
		
		@SuppressWarnings("unchecked")
		ArrayList<User> data = (ArrayList<User>) DataHandling.ReadObject("Data32.obj");
		Tests[] t = (Tests[]) new Tests[data.size()];
		for(int i=0; i<data.size();i++){
			t[i] = new Tests(i, data);
			t[i].start();
		}
		
		for(int i=0; i<data.size(); i++) {
			t[i].join();
		}
		
		try {
			File file = new File("log"+dtf2.format(LocalDateTime.now())+".csv");
			FileWriter outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile);
			writer.writeNext(new String[] {"Recognition Log: [Vigneet Sompura] // [$P] // [MMG] // USER-DEPENDENT RANDOM-100"});
			writer.writeNext(new String[] {"User","GestureType","RandomIteration#","#ofTrainingExamples","sizeOfTrainingSet", "TrainingSetContents","Candidate","RecoginitionResult","Correct?","Score","ResultBestMatchInstance","NBestList"});
			for(int i = 0;i<data.size();i++) {
				List<String[]> logdata = t[i].log;
		        writer.writeAll(logdata);
			}
			writer.close();

			CSVWriter csv = new CSVWriter(new FileWriter(new File("summary"+dtf2.format(LocalDateTime.now())+".csv")));
			csv.writeNext(new String[] {"User/TrainingSetSize","1","2","3","4","5","6","7","8","9"});
			for(int i = 0; i<data.size();i++) {
				String[] s = new String[10];
				s[0] = data.get(i).getUserID();
				for(int j = 0; j<9;j++) {
					s[j+1] = t[i].accuracy[j]+"";
				}
				csv.writeNext(s);
			}
			csv.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(started));
		System.out.println(dtf.format(now));
	}

}
