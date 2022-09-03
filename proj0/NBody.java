public class NBody {
    public static void main(String[] args){
        double T=Double.parseDouble(args[0]);
        double dt=Double.parseDouble(args[1]);
        String filename=args[2];
        double uniRadius=readRadius(filename);
        Planet[] planetList=readPlanets(filename);
        double time=0.0;

        while(time!=T){
            double[] xforce=new double[planetList.length];
            double[] yforce=new double[planetList.length];
            for(int i=0;i<planetList.length;i+=1){
                xforce[i]=planetList[i].calcNetForceExertedByX(planetList);
                yforce[i]=planetList[i].calcNetForceExertedByY(planetList);
            }
            for(int i=0;i<planetList.length;i+=1){
                planetList[i].update(dt, xforce[i], yforce[i]);
            }
            /** Sets up the universe so it goes from 
             * -100, -100 up to 100, 100 */
            StdDraw.setScale(-1.0*uniRadius, uniRadius);

            /* Clears the drawing window. */
            StdDraw.clear();

            StdDraw.enableDoubleBuffering();

            /* Stamps three copies of advice.png in a triangular pattern. */
            StdDraw.picture(0, 0, "images/starfield.jpg");

            for(Planet p: planetList){
                p.draw();
            }

            /* Shows the drawing to the screen, and waits 2000 milliseconds. */
            StdDraw.show();
            StdDraw.pause(10);		

            time+=dt;

        }
        StdOut.printf("%d\n", planetList.length);
        StdOut.printf("%.2e\n", uniRadius);
        for (int i = 0; i < planetList.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                        planetList[i].xxPos, planetList[i].yyPos, planetList[i].xxVel,
                        planetList[i].yyVel, planetList[i].mass, planetList[i].imgFileName);   
}

    }

    public static double readRadius(String filepath){
        In in = new In(filepath);
        int planetNum=in.readInt();
        double UniRadius=in.readDouble();
        return UniRadius;
    }

    public static Planet[] readPlanets(String filepath){
        In in = new In(filepath);
        int planetNum=in.readInt();
        Planet[] planetNameList=new Planet[planetNum];
        double UniRadius=in.readDouble();
        // while(!in.isEmpty()){
        //     planetNameList[planetIndex].xxPos=in.readDouble();
        //     planetNameList[planetIndex].yyPos=in.readDouble();
        //     planetNameList[planetIndex].xxVel=in.readDouble();
        //     planetNameList[planetIndex].yyVel=in.readDouble();
        //     planetNameList[planetIndex].mass=in.readDouble();
        //     planetNameList[planetIndex].imgFileName=in.readString();
        //     planetIndex+=1;
        // }
        for(int i=0;i<planetNum;i+=1){
            double xPos=in.readDouble();
            double yPos=in.readDouble();
            double xVel=in.readDouble();
            double yVel=in.readDouble();
            double mass=in.readDouble();
            String imgfilename=in.readString();
            planetNameList[i]=new Planet(xPos,yPos,xVel,yVel,mass,imgfilename);
        }
        return planetNameList;
    }
}
