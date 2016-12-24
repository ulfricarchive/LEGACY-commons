package com.ulfric.commons.cdi;

@Shared
public class Example {

	public static void main(String[] args)
	{
		Injector i = Injector.newInstance();
		i.bind(SimpleLogger.class).toSelf();
		Example e1 = i.create(Example.class);
		Example e2 = i.create(Example.class);
		System.out.println(e1 == e2);
	}

	private static class SimpleLogger
	{
		public void info(String s)
		{
			System.out.println(s);
		}
	}

	@Inject
	private SimpleLogger logger;

	public void sayHello()
	{
		this.logger.info("Hello!");
	}

}