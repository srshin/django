# Generated by Django 2.1.7 on 2019-03-05 04:24

import blog.models
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('blog', '0010_auto_20190305_1256'),
    ]

    operations = [
        migrations.AlterField(
            model_name='person',
            name='contact_info',
            field=models.CharField(default=blog.models.Person.contact_default, max_length=500),
        ),
    ]
