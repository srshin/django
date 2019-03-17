from django.shortcuts import render
from django.views.generic import ListView
from .models import Outsourcing

# index = ListView.as_view(model=Outsourcing, paginate_by=10, template_name='kmong/list.html')

class ProductListView(ListView):
    model=Outsourcing
    paginate_by=10
    template_name='kmong/list.html'

    def get_context_data(self, **kwargs):
        context = super(ProductListView, self).get_context_data(**kwargs)
        paginator = context['paginator']
        page_numbers_range = 5  # Display only 5 page numbers
        max_index = len(paginator.page_range)

        page = self.request.GET.get('page')
        current_page = int(page) if page else 1

        start_index = int((current_page - 1) / page_numbers_range) * page_numbers_range
        end_index = start_index + page_numbers_range
        if end_index >= max_index:
            end_index = max_index

        page_range = paginator.page_range[start_index:end_index]
        context['page_range'] = page_range
        return context

index = ProductListView.as_view()