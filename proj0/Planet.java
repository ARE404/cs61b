public class Planet {
    public double xxPos,yyPos;
    public double xxVel,yyVel;
    public double mass;
    public String imgFileName;
    private double G=6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        xxPos=xP;
        yyPos=yP;
        xxVel=xV;
        yyVel=yV;
        mass=m;
        imgFileName=img;
    }

    public Planet(Planet b){
        xxPos=b.xxPos;
        yyPos=b.yyPos;
        xxVel=b.xxVel;
        yyVel=b.yyVel;
        mass=b.mass;
        imgFileName=b.imgFileName;
    }

    public double calcDistance(Planet b){
        return Math.pow((xxPos-b.xxPos)*(xxPos-b.xxPos)+(yyPos-b.yyPos)*(yyPos-b.yyPos),0.5);
    }

    public double calcForceExertedBy(Planet b){
        double distance=this.calcDistance(b);
        return G*mass*b.mass/(distance*distance);
    }

    public double calcForceExertedByX(Planet b){
        return calcForceExertedBy(b)*(b.xxPos-xxPos)/this.calcDistance(b);
    }

    public double calcForceExertedByY(Planet b){
        return calcForceExertedBy(b)*(b.yyPos-yyPos)/this.calcDistance(b);
    }

    public double calcNetForceExertedByX(Planet[] pl){
        double totalForceX=0;
        for(Planet b: pl){
            if(b.equals(this)){
                continue;
            }
            totalForceX=totalForceX+calcForceExertedByX(b);
        }
        return totalForceX;
    }

    public double calcNetForceExertedByY(Planet[] pl){
        double totalForceY=0;
        for(Planet b: pl){
            if(b.equals(this)){
                continue;
            }
            totalForceY=totalForceY+calcForceExertedByY(b);
        }
        return totalForceY;
    }

    public void update(double dt, double fx, double fy){
        double ax=fx/mass;
        double ay=fy/mass;
        xxVel+=ax*dt;
        yyVel+=ay*dt;
        xxPos+=xxVel*dt;
        yyPos+=yyVel*dt;
    }

    public void draw(){
		StdDraw.picture(this.xxPos, this.yyPos, "images/"+this.imgFileName);
    }
}
