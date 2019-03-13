from django.shortcuts import render
from .models import Major, Student
from django.views.generic.edit import CreateView, UpdateView, DeleteView
from django.views.generic import ListView, DetailView
# 자동으로 create하고나서 success인 경우 model의 get_absolute_url로 이동함. 

student_list = ListView.as_view(model =  Student, template_name="mainapp/student_all_list.html", paginate_by=10) # #object_list를 가지고 object_list.html로 render 
student_detail = DetailView.as_view(model = Student, template_name="mainapp/student_view_detail.html")  #object를 가지고 object_detail.html로 render 
student_new = CreateView.as_view(model =  Student, template_name="mainapp/student_input_form.html", fields='__all__') # object_form.html 
student_edit = UpdateView.as_view(model = Student, template_name="mainapp/student_input_form.html", fields='__all__') # object_form.html
student_delete = DeleteView.as_view(model =  Student, template_name="mainapp/student_delete.html", success_url='/main/student/list') # object_confirm_delete.html


#major_list = ListView.as_view(model =  Major, template_name="mainapp/major_all_list.html", paginate_by=10) # #object_list를 가지고 object_list.html로 render 
major_detail = DetailView.as_view(model = Major, template_name="mainapp/major_view_detail.html")  #object를 가지고 object_detail.html로 render 
major_new = CreateView.as_view(model =  Major, template_name="mainapp/major_input_form.html", fields='__all__') # object_form.html 
major_edit = UpdateView.as_view(model = Major, template_name="mainapp/major_input_form.html", fields='__all__') # object_form.html
major_delete = DeleteView.as_view(model =  Major, template_name="mainapp/major_delete.html", success_url='/main/major/list') # object_confirm_delete.html

class MajorListView(ListView):
    model=Major
    paginate_by=10
    template_name='mainapp/major_all_list.html'

    def get_context_data(self, **kwargs):
        context = super().get_context_data(**kwargs)
        print (context)
        paginator = context['paginator']
        page_numbers_range = 10  # Display 5 page numbers
        max_index = len(paginator.page_range)

        page = self.request.GET.get('page')
        print (self.request)
        current_page = int(page) if page else 1

        start_index = int((current_page - 1) / page_numbers_range) * page_numbers_range
        end_index = start_index + page_numbers_range
        if end_index >= max_index:
            end_index = max_index

        page_range = paginator.page_range[start_index:end_index]
        context['page_range'] = page_range
        return context

major_list = MajorListView.as_view()

def index(request):
    response = render(request, 'mainapp/index.html', {'message':"Student Project"})    
    return response

def major_search():
    pass

def student_search():
    pass