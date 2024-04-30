package com.eliftekin.inviochallenge.ui.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.eliftekin.inviochallenge.databinding.FragmentUniWebBinding;
import com.eliftekin.inviochallenge.ui.viewmodels.UniWebViewModel;

public class UniWebFragment extends Fragment {

    private FragmentUniWebBinding binding;

    private WebView webView;
    String uniName;
    String uniUrl;

    UniWebViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUniWebBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(UniWebViewModel.class);

        Bundle bundle = getArguments();
        uniName = bundle.getString("uniName");
        uniUrl = bundle.getString("url");

        binding.uniName.setText(uniName);

        setWebView(uniUrl);

        binding.backBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });

        return binding.getRoot();
    }

    private void setWebView(String uniUrl) {
        webView = binding.uniWebView;

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                    binding.progressBar.setVisibility(View.VISIBLE);
                else
                    binding.progressBar.setVisibility(View.GONE);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                viewModel.setIsLoading(true);

            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                viewModel.setIsLoading(false);

            }
        });

        webView.loadUrl(uniUrl);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }
}