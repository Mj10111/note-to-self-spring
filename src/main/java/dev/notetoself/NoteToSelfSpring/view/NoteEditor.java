package dev.notetoself.NoteToSelfSpring.view;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import dev.notetoself.NoteToSelfSpring.model.Note;
import dev.notetoself.NoteToSelfSpring.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.plaf.basic.BasicMenuUI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringComponent
@UIScope
public class NoteEditor extends VerticalLayout implements KeyNotifier {

    private final NoteRepository repository;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private Note note;

    TextArea noteContent = new TextArea("Note Content");

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel", VaadinIcon.ARROW_CIRCLE_LEFT_O.create());
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());

    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Note> binder = new Binder<>(Note.class);

    private ChangeHandler changeHandler;

    @Autowired
    public NoteEditor(NoteRepository repository){
        this.repository = repository;
        this.setAlignItems(Alignment.CENTER);
        noteContent.setWidth("50%");
        noteContent.setHeight("250px");

        add(noteContent, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        //addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> cancel());
        setVisible(false);
    }

    void cancel(){
        setVisible(false);
    }

    void delete() {
        repository.delete(note);
        changeHandler.onChange();
    }

    void save(){
        if(note.getDateCreated() == null) {
            note.setDateCreated(LocalDateTime.now().format(dtf));
            repository.save(note);
        } else {
            note.setDateUpdated(LocalDateTime.now().format(dtf));
            repository.save(note);
        }
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editNote(Note n) {
        if(n == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = n.getId() != null;
        if(persisted){
            note = repository.findById(n.getId()).get();
        } else {
            note = n;
        }
        cancel.setVisible(persisted);

        binder.setBean(note);

        setVisible(true);

        noteContent.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}


