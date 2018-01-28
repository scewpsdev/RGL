# RGL: Rabbit Game Library

A game utility library for Java which uses LWJGL 3.1.2.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Installing

In order to use the library or build it from source you will need to have the latest JDK installed.
You can get it here:
http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

The code is in an eclipse project format. It is recommended to use it with eclipse or Visual Studio Code.
Using a different editor/IDE is also possible, but you'll have to setup the classpath, the source folder and the library references youself.

### Example

Here is a simple triangle example using the library. It covers creating an OpenGL display, vertex arrays, element array buffers, shaders and textures.

```Java
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
```

## Built With

* [LWJGL 3.1.2] https://www.lwjgl.org/ Used for native access to OpenGL and GLFW.

## Contributing

Pull requests will not be accepted for now, I'd be happy for any suggestions and bug reports though!

## Authors

* **RagingRabbit**

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
