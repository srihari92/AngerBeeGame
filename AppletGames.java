import java.awt.image.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.ImageIcon;
//<applet code=AppletGames width=500 height=500></applet>
public class AppletGames extends Applet implements Runnable,ActionListener
{
	Image img[],background,bee,rest,gameover;
	int index=0,i=0,j=0,countx=0,statusf=0;
	int maxlen,x=0,y=300,ox=0,oy=0,difx=50,dify=50,delay=1000,sco=0,live=3;
	MediaTracker tracker;
	Thread t;
	boolean ck=true;
	boolean gamestoped=true,s=false,pas=false,over=true;
	Button lefto,righto,centero,pause,restart;
	Label l1;
	int rand[][]={{1,2,3},
			{1,3,2},
			{2,1,3},
			{2,3,1},
			{3,2,1},
			{3,1,2}};
	public void run(){
		while(true)
		{
			try
			{
				Thread.sleep(delay);
				repaint();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public void init()
	{
		img=new Image[live];
		setLayout(null);
		tracker=new MediaTracker(this);
		lefto=new Button("Left");
		righto=new Button("Right");
		centero=new Button("Center");
		pause=new Button("Pause");
		restart=new Button("Reset");

		l1=new Label("Socre: 0        Life: 3");

		add(lefto);
		lefto.setBounds(50,450,35,35);

		add(l1);
		l1.setBounds(230, 484, 100, 15);
		add(righto);
		righto.setBounds(450,450,35,35);

		add(centero);
		centero.setBounds(250,450,35,35);

		add(pause);
		pause.setBounds(465,0,35,15);

		add(restart);
		restart.setBounds(465,15,35,15);

		pause.addActionListener(this);
		restart.addActionListener(this);
		centero.addActionListener(this);
		lefto.addActionListener(this);
		righto.addActionListener(this);		
		try
		{
			for(int i=0;i<live;i++)
			{
				img[i]=getImage(getDocumentBase(),i+".gif");
				tracker.addImage(img[i],i);
			}
			background=getImage(getDocumentBase(),"background.jpg");
			bee=getImage(getDocumentBase(),"bee.gif");
			rest=getImage(getDocumentBase(),"pause.jpg");
			gameover=getImage(getDocumentBase(),"gameover.jpg");
			tracker.addImage(background,live);
			tracker.addImage(bee,live+1);
			tracker.addImage(rest, live+2);
			tracker.addImage(gameover, live+3);
			tracker.waitForAll();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void start()
	{
		t=new Thread(this);
		t.start();
	}
	public void stop()
	{

		t.stop();

		t=null;
	}
	public void paint(Graphics g)
	{
		if(pas)
		{
			g.drawImage(rest,0,0,this);
		}
		if(over==false)
		{
			g.drawImage(gameover, 0,0, this);
			t.stop();
		}

		if(gamestoped)
		{
			g.drawImage(background,0,0,this);
			g.drawImage(img[statusf],x,y,this); // the main player
			ck=true;


			//draw circle
			if(countx >5){
				if(i<6)
				{
					if(j<3)
					{
						switch (rand[i][j])
						{
						case 1:
							ox=0;
							break;
						case 2:
							ox=200;
							break;
						case 3:
							ox=400;
							break;
						}
						j++;				
					}
					else
					{
						i++;
						j=0;
					}

				}
				else
				{
					i=0;
				}
				countx=0;
			}
			else{
				countx++;
			}
			//condition for elimination
			dify=Math.abs(y-oy);
			difx=Math.abs(x-ox);
			g.setColor(Color.yellow);//do sum thing
			if(dify<35 && difx<35)
			{
				live--;
				if(live>=0)
					statusf++;
				ck=false;
			}
			g.drawImage(bee,ox,oy,this);
			if(oy<250)
			{
				oy+=30;
			}
			else
			{
				if(ck)
				{
					sco++;	
				}
				oy=0;
			}
			if(delay>100)
			{
				delay-=2;
			}
			if(live==0)
			{	
				
				
				over=false;
				//t.stop();

				gamestoped=false;
				repaint();
			}
			l1.setText("Score: "+sco+"  life: "+live+"");
			showStatus("Score"+sco+"  lifes remaining"+live+"");
		}
	}
	public void actionPerformed(ActionEvent ae)
	{

		if(ae.getActionCommand()=="Left")
		{
			x=0;
		}
		if(ae.getActionCommand()=="Right")
		{
			x=400;
		}
		if(ae.getActionCommand()=="Center")
		{
			x=200;
		}		
		if(ae.getActionCommand()=="Reset")
		{
			t.stop();
			t=null;
			x=0;y=300;sco=0;live=3;delay=1050;statusf=0;dify=50;difx=50;ox=0;oy=0;ck=true;s=false;pas=false;
			gamestoped=true;
			pause.setLabel("Pause");
			over=true;
			t=new Thread(this);
			t.start();


		}
		if(over)
		{
			if(ae.getActionCommand()=="Pause")
			{
				if(s==false)
				{
					pas=true;
					repaint();
					s=true;
					gamestoped=false;
					pause.setLabel("Play");
					t.suspend();

				}
			}
			if(ae.getActionCommand()=="Play")
			{

				if(s)
				{
					pas=false;
					pause.setLabel("Pause");
					s=false;
					gamestoped=true;
					t.resume();
				}

			}
		}
	}
}

