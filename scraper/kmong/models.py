from django.db import models

class Outsourcing(models.Model):
    name = models.CharField(verbose_name="판매자", max_length=20)
    title = models.CharField(verbose_name="상품명", max_length=100)
    img_src = models.CharField(verbose_name="이미지링크", max_length=100)
    detail_link = models.CharField(verbose_name="상세링크", max_length=100)
    price = models.IntegerField(verbose_name="가격")

    def __str__(self):
        return self.name