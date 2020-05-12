from django.shortcuts import render
import textwrap

from django.http import HttpResponse
from subprocess import Popen, PIPE, STDOUT

from django import forms
from django.shortcuts import get_object_or_404, render
from django.http import HttpResponseRedirect

RAGA_CHOICES= [
    ('Mohanam', 'Mohanam'),
    ('Abhogi', 'Abhogi'),
    ('ShudhaDhanyasi', 'ShudhaDhanyasi'),
    ]
	
VOICE_CHOICES= [
    ('Sitar', 'Sitar'),
    ('Piccolo', 'Piccolo'),
    ('Accordian', 'Accordian'),
	('Violin','Violin'),
    ]
	
class InputForm(forms.Form):
	name = forms.CharField(max_length=100, label='Your Name')
	raga = forms.CharField(label='Choose your Raga:', widget=forms.Select(choices=RAGA_CHOICES))
	duration = forms.IntegerField(label="Input duration:")
	instrument = forms.CharField(label='Choose Instrumental Voice:', widget=forms.Select(choices=VOICE_CHOICES))

def home_page_view(request):
	submitted = False
	if request.method == 'POST':
		form = InputForm(request.POST)
		if form.is_valid():
			cd = form.cleaned_data
			#p = Popen(['java', '-cp', 'AIComposer_V.2.jar','MainPlay'], stdout=PIPE, stderr=STDOUT)
			p = Popen(['java', '-cp', 'AIComposer_V.2.jar','MainPlay',cd['raga'],str(cd['duration']),cd['instrument']], stdout=PIPE, stderr=STDOUT)
			
			return render(request, 'ai_composer_result.html', {'name':cd['name'],'raga':cd['raga'],'form': form})
	else:
		form = InputForm(initial={'name': 'Shilpa','duration':'64'})
		if 'submitted' in request.GET:
			submitted = True

	return render(request, 'ai_composer.html',{'form': form})	