package com.vogella.jaxb.parts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.vogella.jaxb.model.Book;
import com.vogella.jaxb.model.Bookstore;

public class SamplePart {

	private static final String BOOKSTORE_XML = "./bookstore-jaxb.xml";

	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		Button button = new Button(parent, SWT.PUSH);
		button.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		button.setText("Trigger JAXB");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					testJaxB();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});

	}

	private void testJaxB() throws FileNotFoundException {

		var bookList = new ArrayList<Book>();

		// create books
		var book1 = new Book();
		book1.setIsbn("978-0060554736");
		book1.setName("The Game");
		book1.setAuthor("Neil Strauss");
		book1.setPublisher("Harpercollins");
		bookList.add(book1);

		var book2 = new Book();
		book2.setIsbn("978-3832180577");
		book2.setName("Feuchtgebiete");
		book2.setAuthor("Charlotte Roche");
		book2.setPublisher("Dumont Buchverlag");
		bookList.add(book2);

		// create bookstore, assigning book
		var bookstore = new Bookstore();
		bookstore.setName("Fraport Bookstore");
		bookstore.setLocation("Frankfurt Airport");
		bookstore.setBookList(bookList);

		// create JAXB context and instantiate marshaller
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(Bookstore.class.getPackageName(), Bookstore.class.getClassLoader());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out
			m.marshal(bookstore, System.out);

			// Write to File
			m.marshal(bookstore, new File(BOOKSTORE_XML));

			// get variables from our xml file, created before
			System.out.println();
			System.out.println("Output from our XML File: ");
			Unmarshaller um = context.createUnmarshaller();
			Bookstore bookstore2 = (Bookstore) um.unmarshal(new FileReader(BOOKSTORE_XML));
			List<Book> list = bookstore2.getBooksList();
			for (Book book : list) {
				System.out.println("Book: " + book.getName() + " from " + book.getAuthor());
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}
}