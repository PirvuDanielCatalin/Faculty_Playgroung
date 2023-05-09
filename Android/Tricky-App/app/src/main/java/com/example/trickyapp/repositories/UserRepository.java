package com.example.trickyapp.repositories;

import android.content.Context;
import android.os.AsyncTask;

import com.example.trickyapp.controllers.ApplicationController;
import com.example.trickyapp.databases.AppDatabase;
import com.example.trickyapp.interfaces.OnUserRepositoryActionListener;
import com.example.trickyapp.models.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserRepository {
    private AppDatabase appDatabase;

    public UserRepository(Context context) {
        appDatabase = ApplicationController.getAppDatabase();
    }

    public List<User> getAll(final OnUserRepositoryActionListener listener) {
        try {
            return new GetAllTask(listener).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getByFirstAndLastName(final String first_name, final String last_name, final OnUserRepositoryActionListener listener) {
        try {
            return new GetByFirstAndLastNameTask(listener).execute(first_name, last_name).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(final User user, final OnUserRepositoryActionListener listener) {
        new InsertTask(listener).execute(user);
    }

    public void delete(final User user, final OnUserRepositoryActionListener listener) {
        new DeleteTask(listener).execute(user);
    }

    private class GetAllTask extends AsyncTask<Void, Void, List<User>> {
        OnUserRepositoryActionListener listener;

        GetAllTask(OnUserRepositoryActionListener listener) {
            this.listener = listener;
        }

        @Override
        protected List<User> doInBackground(Void... url) {
            return appDatabase.userDao().getAll();
        }

        @Override
        protected void onPostExecute(List<User> users) {
            super.onPostExecute(users);
            listener.actionSuccess();
        }
    }

    private class GetByFirstAndLastNameTask extends AsyncTask<String, Void, User> {
        OnUserRepositoryActionListener listener;

        GetByFirstAndLastNameTask(OnUserRepositoryActionListener listener) {
            this.listener = listener;
        }

        @Override
        protected User doInBackground(String... strings) {
            return appDatabase.userDao().getByFirstAndLastName(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            listener.actionSuccess();
        }
    }

    private class InsertTask extends AsyncTask<User, Void, Void> {
        OnUserRepositoryActionListener listener;

        InsertTask(OnUserRepositoryActionListener listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(User... users) {
            appDatabase.userDao().insert(users[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listener.actionSuccess();
        }
    }

    private class DeleteTask extends AsyncTask<User, Void, Void> {
        OnUserRepositoryActionListener listener;

        DeleteTask(OnUserRepositoryActionListener listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(User... users) {
            appDatabase.userDao().delete(users[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listener.actionSuccess();
        }
    }
}
