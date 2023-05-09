package com.example.project.repositories;

import android.content.Context;
import android.os.AsyncTask;

import com.example.project.controllers.ApplicationController;
import com.example.project.databases.AppDatabase;
import com.example.project.interfaces.OnBookRepositoryActionListener;
import com.example.project.models.Book;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class BookRepository {
    private AppDatabase appDatabase;

    public BookRepository(Context context) {
        appDatabase = ApplicationController.getAppDatabase();
    }

    public List<Book> getAll(final OnBookRepositoryActionListener listener) {
        try {
            return new GetAllTask(listener).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Book> getAllByIds(final OnBookRepositoryActionListener listener, Integer... ids) {
        try {
            return new GetAllByIdsTask(listener).execute(ids).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Book getById(final Integer id, final OnBookRepositoryActionListener listener) {
        try {
            return new GetByIdTask(listener).execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Book getByTitle(final String string, final OnBookRepositoryActionListener listener) {
        try {
            return new GetByTitleTask(listener).execute(string).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(final Book book, final OnBookRepositoryActionListener listener) {
        new InsertTask(listener).execute(book);
    }

    public void insertAll(final OnBookRepositoryActionListener listener, final Book... books) {
        new InsertAllTask(listener).execute(books);
    }

    public void delete(final Book book, final OnBookRepositoryActionListener listener) {
        new DeleteTask(listener).execute(book);
    }

    public void deleteAll(final OnBookRepositoryActionListener listener, final Book... books) {
        new DeleteAllTask(listener).execute(books);
    }

    // DO NOT PERFORM OPERATION ON MAIN THREAD AS APP WILL CRASH
    // See more details about AsyncTask in the next chapter

    private class GetAllTask extends AsyncTask<Void, Void, List<Book>> {
        OnBookRepositoryActionListener listener;

        GetAllTask(OnBookRepositoryActionListener listener) {
            this.listener = listener;
        }

        @Override
        protected List<Book> doInBackground(Void... url) {
            return appDatabase.bookDao().getAll();
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            super.onPostExecute(books);
            listener.actionSuccess();
        }
    }

    private class GetAllByIdsTask extends AsyncTask<Integer, Void, List<Book>> {
        OnBookRepositoryActionListener listener;

        GetAllByIdsTask(OnBookRepositoryActionListener listener) {
            this.listener = listener;
        }

        @Override
        protected List<Book> doInBackground(Integer... ids) {
            return appDatabase.bookDao().getAllByIds(ids);
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            super.onPostExecute(books);
            listener.actionSuccess();
        }
    }

    private class GetByIdTask extends AsyncTask<Integer, Void, Book> {
        OnBookRepositoryActionListener listener;

        GetByIdTask(OnBookRepositoryActionListener listener) {
            this.listener = listener;
        }

        @Override
        protected Book doInBackground(Integer... ids) {
            return appDatabase.bookDao().getById(ids[0]);
        }

        @Override
        protected void onPostExecute(Book book) {
            super.onPostExecute(book);
            listener.actionSuccess();
        }
    }

    private class GetByTitleTask extends AsyncTask<String, Void, Book> {
        OnBookRepositoryActionListener listener;

        GetByTitleTask(OnBookRepositoryActionListener listener) {
            this.listener = listener;
        }

        @Override
        protected Book doInBackground(String... strings) {
            return appDatabase.bookDao().getByTitle(strings[0]);
        }

        @Override
        protected void onPostExecute(Book book) {
            super.onPostExecute(book);
            listener.actionSuccess();
        }
    }

    private class InsertTask extends AsyncTask<Book, Void, Void> {
        OnBookRepositoryActionListener listener;

        InsertTask(OnBookRepositoryActionListener listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Book... books) {
            appDatabase.bookDao().insert(books[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listener.actionSuccess();
        }
    }

    private class InsertAllTask extends AsyncTask<Book, Void, Void> {
        OnBookRepositoryActionListener listener;

        InsertAllTask(OnBookRepositoryActionListener listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Book... books) {
            appDatabase.bookDao().insertAll(books);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listener.actionSuccess();
        }
    }

    private class DeleteTask extends AsyncTask<Book, Void, Void> {
        OnBookRepositoryActionListener listener;

        DeleteTask(OnBookRepositoryActionListener listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Book... books) {
            appDatabase.bookDao().delete(books[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listener.actionSuccess();
        }
    }

    private class DeleteAllTask extends AsyncTask<Book, Void, Void> {
        OnBookRepositoryActionListener listener;

        DeleteAllTask(OnBookRepositoryActionListener listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Book... books) {
            appDatabase.bookDao().deleteAll(books);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listener.actionSuccess();
        }
    }
}