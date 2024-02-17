package ie.tudublin;

import processing.core.PApplet;



public class Arrays extends PApplet
{
	String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

	float[] rainfall = {200, 260, 300, 150, 100, 50, 10, 40, 67, 160, 400, 420};
	float rows =  10;

	int mode = 0;

	public float map1(float a, float b, float c, float d, float e)
	{
		float r1 = c -b;
		float r2 = e - d;

		float howFar = a - b;
		return d + (howFar / r1) * r2;
	}

	void randomize()
	{
		for (int i = 0; i < rainfall.length; i++) {
			rainfall[i] = random(500);
		}
	}

	public void keyPressed() {
		if (key == '0') {
			mode = 0;
		} else if (key == '1') {
			mode = 1;
		} else if (key == '2') {
			mode = 2;
		}
		
	}

	public void settings()
	{
		size(600, 600);
		//fullScreen();

		String[] m1 = months;
		print(m1[0]);
		for(int i = 0; i < months.length; i ++)
		{
			println("Month: " + months[i] + "\t" + rainfall[i]);
		}
		for (String s : m1) {
			println(s);
		}

		int minIndex = 0;
		for(int i= 0 ; i < rainfall.length ; i ++)
		{
			if (rainfall[i] < rainfall[minIndex])
			{
				minIndex = i;
			}
		}
		
		int maxIndex = 0;
		for(int i= 0 ; i < rainfall.length ; i ++)
		{
			if (rainfall[i] > rainfall[maxIndex])
			{
				maxIndex = i;
			}
		}

		println("The month with the minimum rainfall was " + months[minIndex] + " with " + rainfall[minIndex] + "rain");
		println("The month with the max rainfall was " + months[maxIndex] + " with " + rainfall[maxIndex] + "rain");
		
		
		float tot = 0;
		for(float f:rainfall)
		{
			tot += f;
		}

		float avg = tot / (float) rainfall.length;

		// a b-c d-e;
		println(map1(5, 0, 10, 0, 100));
		// 50

		println(map1(25, 20, 30, 200, 300));
		// 250

		println(map1(26, 25, 35, 0, 100));
		// 10 


	}

	public void setup() {
		colorMode(HSB, 360, 255, 255);
		background(0);
		randomize();
		
	}

	
	public void draw()
	{	

		switch(mode) {
			case 0:
				background(0);
				stroke(255);
				
				float paddingX = width/(float)months.length - 3;
				float paddingY = height / 10;
		
				float w = (width - paddingX * 2)/months.length;
				float y = height - paddingY;
				
				for(int i = 0 ; i < months.length ;  i ++)
				{
					
					float x = map1(i,0, months.length, paddingX, width - paddingX);
					float h = map1(rainfall[i], 0, 500, 0, height - (paddingY * 2));
					float hue = map1(i, 0, months.length, 0, 360);
					fill(hue, 255, 255);
					rect(x, y, w, -h);
		
					fill(255);
					text(months[i],x + (w/2), y + (height/30));
		
				}
		
				float rainfallX = paddingX - (width/20);
		
				for(int i = 0 ; i < rows ; i ++)
				{
					float rainfallY = map1(i, 0, rows - 1, height-paddingY, paddingY);
					float denom = map1(i, 0, rows, 0, 500);
		
					fill(255);
					text((int)denom, rainfallX, rainfallY);
				}
		
				stroke(255);
				line(width/15, paddingY, width/15, y);
		
				textSize(15);
				textAlign(CENTER, TOP);
				fill(255);
				text("Rainfall bar chart", width / 2, height / 50);
			break;
		
			case 1:
				background(0);
				stroke(255);

				float paddingX2 = width/(float)months.length - 3;
				float paddingY2 = height / 10;

				float w2 = (width - paddingX2 * 2)/months.length;
				float yBase = height - paddingY2; 

				float x1 = paddingX2 + w2/2;
				float h1 = map1(rainfall[0], 0, 500, 0, height - (paddingY2 * 2));
				float y1 = yBase - h1;

				for(int i = 0 ; i < months.length ;  i ++) {
					float x2 = paddingX2 + w2/2 + i * w2;
					float h2 = map1(rainfall[i], 0, 500, 0, height - (paddingY2 * 2));
					float y2 = yBase - h2;

					line(x1, y1, x2, y2);

					x1 = x2;
					y1 = y2;

					fill(255);
					text(months[i], x2, yBase + (height/30));
				}

				float rainfallX2 = paddingX2 - (width/20);
				for(int i = 0 ; i < rows ; i ++) {
					float rainfallY = map1(i, 0, rows - 1, height-paddingY2, paddingY2);
					float denom = map1(i, 0, rows, 0, 500);

					fill(255);
					text((int)denom, rainfallX2, rainfallY);
				}

				stroke(255);
				line(paddingX2, paddingY2, paddingX2, yBase);

				textSize(15);
				textAlign(CENTER, TOP);
				fill(255);
				text("Rainfall Trend Chart", width / 2, height / 50);
			break;

			case 2:
				background(0);

				stroke(255);
				float paddingX3 = width/(float)months.length - 3;
				float paddingY3 = height / 10;
				float lastAngle = 0;
				float total = 0;
				
				for (int i = 0; i < rainfall.length; i++) {
					total += rainfall[i];
				}
				
				for (int i = 0; i < rainfall.length; i++) {
					float hue = map(i, 0, rainfall.length, 0, 255);
					fill(hue, 255, 255); 
					float angle = TWO_PI * (rainfall[i] / total);
					arc(width / 2, height / 2, width - paddingX3 * 2, height - paddingY3 * 2, lastAngle, lastAngle + angle);
					float middleAngle = lastAngle + angle / 2;
					float labelRadius = (width - paddingX3 * 2) / 2 +10; 
					float labelX = width / 2 + cos(middleAngle) * labelRadius;
					float labelY = height / 2 + sin(middleAngle) * labelRadius;
					fill(255); 
					textSize(12); 
					textAlign(CENTER, CENTER); 
					text(months[i], labelX, labelY);
				  
					lastAngle += angle;
				  

					textSize(17);
					textAlign(CENTER, TOP);
					fill(255);
					text("Rainfall Pie Chart", width / 2, (paddingY3 / 2) - 20 ); 
				}
				break;
			
		}
	}
}
