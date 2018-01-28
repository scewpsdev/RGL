package test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import net.rabbit.rgl.core.context.Display;
import net.rabbit.rgl.core.file.FileInput;
import net.rabbit.rgl.core.opengl.Shader;
import net.rabbit.rgl.core.opengl.Texture;
import net.rabbit.rgl.core.opengl.Texture2D;
import net.rabbit.rgl.core.opengl.VertexArray;
import net.rabbit.rgl.core.opengl.Buffer.BufferUsage;
import net.rabbit.rgl.core.opengl.VertexArray.DrawMode;

public class Test {

    public static void main(String[] args) {

		// Set up the display for rendering
		Display display = new Display(800, 600, "Title");

		// Set the display background for clearing
		display.setBackground(Color.DARK_GRAY);

		VertexArray vao;
		Shader shader;
		Texture texture;

		vao = new VertexArray();
		// Add a position buffer
		vao.addBuffer(0, 2, new float[] { -0.5f, -0.5f, 0.5f, -0.5f, 0.0f, 0.5f }, BufferUsage.StaticDraw);
		// Add a color buffer
		vao.addBuffer(1, 3, new float[] { 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f },
				BufferUsage.StaticDraw);
		// Add an element buffer
		vao.addElementBuffer(new int[] { 0, 1, 2 }, BufferUsage.StaticDraw);

		// Load the shader source files
		String vertexSrc = FileInput.loadString(new File("src/test/tri.vs.glsl"));
		String fragmentSrc = FileInput.loadString(new File("src/test/tri.fs.glsl"));
		shader = new Shader(vertexSrc, fragmentSrc);

		// Load the texture from an image file
		texture = new Texture2D();
		BufferedImage image = FileInput.loadImage(new File("src/test/image.png"));
		texture.setData(image.getWidth(), image.getHeight(),
				image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth()));

		// Bind the shader and texture, enable the necessary attributes for rendering
		shader.enable();
		texture.bind();
		vao.enableAttributes();

		while (display.isOpen()) {
			// Swap the display buffers, clear the screen and poll system events
			display.update();

			// Draw the vertex array to the screen
			vao.draw(DrawMode.Triangles);
		}

		// Clean up
		texture.close();
		shader.close();
		vao.close();

		display.close();
	}

}