from django.shortcuts import render
from .models import Major
from django.views.generic.edit import CreateView, UpdateView, DeleteView
from django.views.generic import ListView, DetailView
# 자동으로 create하고나서 success인 경우 model의 get_absolute_url로 이동함. 
major_list = ListView.as_view(model =  Major, template_name="mainapp/major_all_list.html", paginate_by=10) # #object_list를 가지고 object_list.html로 render 
major_detail = DetailView.as_view(model = Major, template_name="mainapp/major_view_detail.html")  #object를 가지고 object_detail.html로 render 
major_new = CreateView.as_view(model =  Major, template_name="mainapp/major_input_form.html", fields='__all__') # object_form.html 
major_edit = UpdateView.as_view(model = Major, template_name="mainapp/major_input_form.html", fields='__all__') # object_form.html
major_delete = DeleteView.as_view(model =  Major, template_name="mainapp/major_delete.html", success_url='/main/') # object_confirm_delete.html
def major_search():
    pass