package com.edusofter.educv.view;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edusofter.educv.R;

public class ActivityAbout extends Activity {

	private static final float BIRD_ACCEL = 1.5f;
	private static final float FACTOR_SIZE = 0.3f;
	private static final int BIRD_DELAY = 600;
	private static final int BIRD_MIN_DURATION = 1000;
	private static final int BIRD_FLIGHTS = 2;
	private static final int BIRD_AMOUNT = 15;
	protected static final Object MEANING_OF_LIFE_UNIVERSE_AND_EVERYTHING = "42";
	private int screenHeight;
	private int screenWidth;
	private MediaPlayer media;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		screenHeight = displaymetrics.heightPixels;
		screenWidth = displaymetrics.widthPixels;
		media = MediaPlayer.create(this, R.raw.ending);
		media.setLooping(true);
		setUpViews();
		setUpEasterEgg();

		// test only CHANGE for release
//		findViewById(R.id.textQue1).setVisibility(View.INVISIBLE);
//		findViewById(R.id.layoutTexto).setVisibility(View.GONE);
//		launchFinalAnimation();
	}
	
	@Override
	protected void onPause() {
		if(media != null) {
			if(media.isPlaying()){
				media.stop();
			}
			media.release();
			media = null;
		}
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		if(media == null){
			media = MediaPlayer.create(this, R.raw.ending);
		}
		super.onResume();
	}

	private void setUpEasterEgg() {
		((TextView) findViewById(R.id.textQue1)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setVisibility(View.INVISIBLE);
				findViewById(R.id.textQue2).setVisibility(View.VISIBLE);
			}
		});
		((TextView) findViewById(R.id.textQue2)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setVisibility(View.INVISIBLE);
				findViewById(R.id.textQue3).setVisibility(View.VISIBLE);
			}
		});
		((TextView) findViewById(R.id.textQue3)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setVisibility(View.INVISIBLE);
				findViewById(R.id.textQue4).setVisibility(View.VISIBLE);
			}
		});
		((TextView) findViewById(R.id.textQue4)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v.setClickable(false);
				v.setVisibility(View.INVISIBLE);
				launchAnimation1();
			}
		});
	}

	protected void launchAnimation1() {
		LinearLayout layoutText = (LinearLayout) findViewById(R.id.layoutTexto);
		RelativeLayout fondo = (RelativeLayout) findViewById(R.id.layoutExterno);

		AnimatorSet animator = new AnimatorSet();
		ObjectAnimator alp = ObjectAnimator.ofFloat(layoutText, "alpha", 1f, 0f);
		alp.setDuration(1000);
		ObjectAnimator fade = ObjectAnimator.ofObject(fondo, "backgroundColor", new ArgbEvaluator(),
				getResources().getColor(R.color.grey_background), getResources().getColor(R.color.black));
		fade.setDuration(2000);

		animator.play(fade).after(alp);
		animator.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				launchAnimation2();
			}

			@Override
			public void onAnimationCancel(Animator animation) {
			}
		});
		animator.start();
	}

	protected void launchAnimation2() {
		TextView text = (TextView) findViewById(R.id.textAnimationQue);
		String[] stringsAnimation = getResources().getStringArray(R.array.animation_que);
		text.setText(stringsAnimation[0]);
		text.setVisibility(View.VISIBLE);
		AnimatorSet anim = new AnimatorSet();
		anim.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				findViewById(R.id.radioAnswers).setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationCancel(Animator animation) {
			}
		});
		anim.playSequentially(getTextAnimators(text, stringsAnimation));
		anim.start();
	}

	private List<Animator> getTextAnimators(final TextView text, final String[] stringsAnimation) {
		List<Animator> anims = new ArrayList<Animator>();
		ObjectAnimator size = ObjectAnimator.ofFloat(text, "textSize",
				getResources().getDimension(R.dimen.text_animation_que_small),
				getResources().getDimension(R.dimen.text_animation_que_large));
		size.setStartDelay(300);
		size.setDuration(300);
		anims.add(size);
		for (int i = 1; i < stringsAnimation.length; i++) {
			size = ObjectAnimator.ofFloat(text, "textSize",
					getResources().getDimension(R.dimen.text_animation_que_small), getResources()
							.getDimension(R.dimen.text_animation_que_large));
			size.addListener(new MyAnimatorListener(text, stringsAnimation[i]));
			size.setStartDelay(0);
			size.setDuration(500);
			anims.add(size);
		}
		return anims;
	}

	private void setUpViews() {
		((TextView) findViewById(R.id.textEmail)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",
						"edumartinezber@gmail.com", null));
				intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.emailSendSubject));
				intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.emailSendBody));
				startActivity(Intent.createChooser(intent, getString(R.string.emailSend)));
			}
		});

		RadioGroup radioAnswers = (RadioGroup) findViewById(R.id.radioAnswers);
		radioAnswers.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.radioQue3) {
					RadioButton but = (RadioButton) findViewById(checkedId);
					but.setChecked(false);
					launchAnimation3();
				} else {
					Toast.makeText(getBaseContext(), getString(R.string.no_magic_word), Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
	}

	protected void launchAnimation3() {
		findViewById(R.id.textAnimationQue).setVisibility(View.GONE);
		findViewById(R.id.radioAnswers).setVisibility(View.GONE);
		RelativeLayout fondo = (RelativeLayout) findViewById(R.id.layoutExterno);
		ObjectAnimator fade = ObjectAnimator.ofObject(fondo, "backgroundColor", new ArgbEvaluator(),
				getResources().getColor(R.color.black), getResources().getColor(R.color.blue_twitter));
		fade.setDuration(3000);
		fade.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				launchAnimation4();
			}

			@Override
			public void onAnimationCancel(Animator animation) {
			}
		});
		fade.start();
	}

	protected void launchAnimation4() {
		launchBirds(BIRD_AMOUNT);
	}

	protected void launchBirds(int count) {
		AnimatorSet anim = new AnimatorSet();
		anim.playTogether(getBirdAnimations(count));
		anim.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				launchAnimation5();
			}

			@Override
			public void onAnimationCancel(Animator animation) {
			}
		});
		anim.start();

	}

	protected void launchAnimation5() {
		ImageView bird = (ImageView) findViewById(R.id.twitterLogo);
		ObjectAnimator anim = ObjectAnimator.ofFloat(bird, "translationX", 0);
		anim.setDuration(1500);
		anim.setInterpolator(new DecelerateInterpolator(1.5f));
		anim.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				RadioGroup pythonAnswers = (RadioGroup) findViewById(R.id.radioPythonAnswers);
				pythonAnswers.setVisibility(View.VISIBLE);
				pythonAnswers.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (checkedId == R.id.radioPython2) {
							RadioButton rad = (RadioButton) group.findViewById(checkedId);
							rad.setChecked(false);
							launchAnimation6();
						} else {
							Toast.makeText(getBaseContext(), getString(R.string.python_ni),
									Toast.LENGTH_SHORT).show();
						}
					}
				});
				findViewById(R.id.textTwitter).setVisibility(View.VISIBLE);
				findViewById(R.id.textTwitter).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(
								Intent.ACTION_VIEW,
								Uri.parse("https://twitter.com/edu_puni"));
						startActivity(intent);					}
				});
			}

			@Override
			public void onAnimationCancel(Animator animation) {
			}
		});
		anim.start();
	}

	protected void launchAnimation6() {
		findViewById(R.id.radioPythonAnswers).setVisibility(View.GONE);
		findViewById(R.id.twitterLogo).setVisibility(View.GONE);
		findViewById(R.id.textTwitter).setVisibility(View.GONE);
		RelativeLayout fondo = (RelativeLayout) findViewById(R.id.layoutExterno);
		ObjectAnimator fade = ObjectAnimator.ofObject(fondo, "backgroundColor", new ArgbEvaluator(),
				getResources().getColor(R.color.blue_twitter), getResources().getColor(R.color.purple_deep));
		fade.setDuration(3000);
		fade.start();
		fade.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				launchAnimation7();
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}
		});
	}

	protected void launchAnimation7() {
		findViewById(R.id.deep_thought).setVisibility(View.VISIBLE);
		findViewById(R.id.textMeaning).setVisibility(View.VISIBLE);
		EditText edit = (EditText) findViewById(R.id.editMeaning);
		edit.setVisibility(View.VISIBLE);
		edit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.toString().equals(MEANING_OF_LIFE_UNIVERSE_AND_EVERYTHING)){
					launchFinalAnimation();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	protected void launchFinalAnimation() {
		EditText edit = (EditText) findViewById(R.id.editMeaning);
		edit.setEnabled(false);
		edit.setVisibility(View.GONE);
		findViewById(R.id.deep_thought).setVisibility(View.GONE);
		findViewById(R.id.textMeaning).setVisibility(View.GONE);
		RelativeLayout fondo = (RelativeLayout) findViewById(R.id.layoutExterno);
		ObjectAnimator fade = ObjectAnimator.ofObject(fondo, "backgroundColor", new ArgbEvaluator(),
				getResources().getColor(R.color.purple_deep), getResources().getColor(R.color.black));
		fade.setDuration(3000);
		fade.start();
		fade.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				launchAnimation8();
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}
		});
		
	}
	
	protected void launchAnimation8() {
		final TextView text = (TextView)findViewById(R.id.textMonkey);
		AnimatorSet anim = new AnimatorSet();
		anim.playSequentially(getFinalTextAnimators(text,getResources().getStringArray(R.array.monkeyEnding)));
		anim.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				text.setKeepScreenOn(true);
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				text.setText(getString(R.string.finalMonkey));
				text.setAlpha(1f);
				text.setKeepScreenOn(false);
				text.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",
								"edumartinezber@gmail.com", null));
						intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.emailSendSubjectFinal));
						intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.emailSendBodyFinal));
						startActivity(Intent.createChooser(intent, getString(R.string.emailSend)));
					}
				});
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				
			}
		});
		anim.start();
		media.start();
	}

	private List<Animator> getFinalTextAnimators(TextView text, String[] stringsAnimation) {
		List<Animator> anims = new ArrayList<Animator>();
		ObjectAnimator alpha;
		ObjectAnimator beta;
		for(int i = 0 ; i < stringsAnimation.length ; i++){
			alpha = ObjectAnimator.ofFloat(text, "alpha", 0f,1f);
			alpha.addListener(new MyAnimatorListener(text,stringsAnimation[i]));
			anims.add(alpha);
			beta = ObjectAnimator.ofFloat(text, "alpha", 1f,0f);
			beta.setStartDelay(3000);
			anims.add(beta);
		}
		return anims;
	}

	private List<Animator> getBirdAnimations(int count) {
		List<Animator> birdAnimations = new ArrayList<Animator>();
		int birdWidth = getResources().getDrawable(R.drawable.twitter_logo).getIntrinsicWidth();

		for (int i = 0; i < count; i++) {
			// SET UP BIRD SCALE AND INITIAL POSITION
			ImageView bird = new ImageView(getBaseContext());
			bird.setImageDrawable(getResources().getDrawable(R.drawable.twitter_logo));
			float factor = (float) Math.random(); // get random number
			float scale = FACTOR_SIZE + factor; // get bird scale
			bird.setScaleX(scale);
			bird.setScaleY(scale);
			((RelativeLayout) findViewById(R.id.layoutExterno)).addView(bird);
			float offsetY = screenHeight * factor; // initial Y
			float offsetX = 0 - birdWidth; // initial X, outside screen by bird
											// width
			bird.setX(offsetX);
			bird.setY(offsetY);

			// CALCULATE FINAL POSITION
			float endFactor = (float) Math.random();
			float endX = screenWidth + birdWidth; // ending X, outside screen by
													// bird width
			float endY = screenHeight - screenHeight * (factor + FACTOR_SIZE); // ending
																				// Y
			AnimatorSet anim = new AnimatorSet();
			ObjectAnimator fly = ObjectAnimator.ofFloat(bird, "x", endX);
			ObjectAnimator fly2 = ObjectAnimator.ofFloat(bird, "y", endY);
			int duration = BIRD_MIN_DURATION + (int) (BIRD_MIN_DURATION * endFactor);
			int delay = (int) (BIRD_DELAY * endFactor);
			fly.setRepeatCount(BIRD_FLIGHTS);
			fly.setDuration(duration);
			fly2.setRepeatCount(BIRD_FLIGHTS);
			fly2.setDuration(duration);
			fly.setStartDelay(delay);
			anim.play(fly).with(fly2);
			anim.setInterpolator(new AccelerateInterpolator(BIRD_ACCEL));
			birdAnimations.add(anim);
		}
		return birdAnimations;
	}

	private class MyAnimatorListener implements AnimatorListener {
		private TextView text;
		private String animationString;

		public MyAnimatorListener(TextView text, String animationString) {
			this.text = text;
			this.animationString = animationString;
		}

		@Override
		public void onAnimationCancel(Animator animation) {
		}

		@Override
		public void onAnimationEnd(Animator animation) {
		}

		@Override
		public void onAnimationRepeat(Animator animation) {
		}

		@Override
		public void onAnimationStart(Animator animation) {
			text.setText(animationString);
		}

	}
}
