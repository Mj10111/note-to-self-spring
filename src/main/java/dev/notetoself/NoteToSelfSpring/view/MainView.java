package dev.notetoself.NoteToSelfSpring.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import dev.notetoself.NoteToSelfSpring.model.Note;
import dev.notetoself.NoteToSelfSpring.repository.NoteRepository;
import org.springframework.util.StringUtils;
import com.vaadin.flow.component.textfield.TextField;

@Route
@CssImport(value="/styles/custom-style.css")
public class MainView extends VerticalLayout {

    private final NoteRepository repo;
    final TextField filter;

    final Image logo;

    private final NoteEditor editor;
    final Grid<Note> grid;

    private final Button addNewBtn;

    public MainView(NoteRepository repo, NoteEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(Note.class);
        this.filter = new TextField();
        this.logo = new Image("Note-To-Self.png", "Note-to-Self");
        this.addNewBtn = new Button("New Note", VaadinIcon.PLUS.create());
        this.setAlignItems(Alignment.CENTER);


        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(logo, actions, grid, editor);

        grid.setHeight("400px");
        grid.setColumns("id", "noteContent", "dateCreated", "dateUpdated");
        grid.getColumnByKey("id").setWidth("100px").setFlexGrow(0);

        filter.setPlaceholder("Find note containing....");

        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listNotes(e.getValue()));


        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editNote(e.getValue());
        });

        addNewBtn.addClickListener(e -> editor.editNote(new Note("", null, null)));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listNotes(filter.getValue());
        });

        listNotes(null);
    }

    private void listNotes(String filterText) {
        if(StringUtils.hasText(filterText)) {
            grid.setItems(repo.findByNoteContentContainsIgnoreCase(filterText));
        } else {
            grid.setItems(repo.findAll());
        }
    }

}
