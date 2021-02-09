import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

interface Equation {
    double compute(float x, float y);
}

class Image {
    static int PBMCount = 0;
    public int[][] image_array;
    private int x_size, y_size;
    public Image(int x, int y){
        this.image_array = new int[y][x];
        this.x_size = x;
        this.y_size = y;
    }

    public void setPixel(int x, int y, int color){
        int xPos = -x + x_size / 2;
        int yPos = y + y_size/2;
        if(xPos < 0 || xPos >= x_size) return;
        if(yPos < 0 || yPos >= x_size) return;
        image_array[yPos][xPos] = color;
    }

    public void drawEquation(Equation equation, double epsilon){
        for(int y = -y_size / 2; y < y_size / 2 + 1; y++){
            for(int x = -x_size / 2; x < x_size / 2 + 1; x++){
                if(Math.abs(equation.compute(x, y)) < epsilon) setPixel(x, y, 255);
            }
        }
    }

    public void createPBM(){
        File pbm = new File("image.pbm");
        try {
            pbm.createNewFile();
            PrintWriter writer = new PrintWriter("PBMImage"+ PBMCount + ".pbm", "UTF-8");
            PBMCount++;
            writer.println("P1");
            writer.println(x_size + " " + y_size);
            for (int i = 0; i < 500; i++) {
                for (int j = 0; j < 500; j++) {
                    writer.print(image_array[j][i] == 0 ? "0" : "1");
                }
                writer.println();
            }
            writer.close();
        } catch(IOException e) {
            System.out.println("Could not create file " + pbm.getName());
        }
    }

}


public class Main {
    public static void main(String[] args) {
        Image image = new Image(500, 500);
        
            for (int i = 0; i < 10; i++) {
                int finalI = i;
                image.drawEquation((x, y) -> Math.cbrt(x) + Math.cbrt(y) - finalI * finalI * Math.sin(x*y), 3.95);
                //image.drawEquation((x, y) -> Math.cbrt(x * x) + Math.cbrt(y * y) - finalI * finalI * Math.cos(x*y), 15);
            }
            image.createPBM();
        
    }

}
