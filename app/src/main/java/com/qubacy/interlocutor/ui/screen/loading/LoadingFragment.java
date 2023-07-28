package com.qubacy.interlocutor.ui.screen.loading;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.qubacy.interlocutor.R;
import com.qubacy.interlocutor.ui.screen.NavigationFragment;

public class LoadingFragment extends NavigationFragment {

    private AnimatorSet m_progressIndicatorAnimatorSet = null;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_main_loading, container, false);

        m_progressIndicatorAnimatorSet =
                (AnimatorSet) AnimatorInflater.loadAnimator(
                        m_context, R.animator.main_loading_progress_bar_animator);

        m_progressIndicatorAnimatorSet.setTarget(
                view.findViewById(R.id.fragment_main_loading_progress_bar_foreground));
        m_progressIndicatorAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                onLoadingEnded();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        m_progressIndicatorAnimatorSet.start();
    }

    private void onLoadingEnded() {
        Navigation.
                findNavController(getView()).
                navigate(R.id.action_loadingFragment_to_mainMenuFragment);
    }
}
