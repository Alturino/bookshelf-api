import {
  BadRequestException,
  Injectable,
  NotFoundException,
} from '@nestjs/common';
import { Book } from '@prisma/client';
import { PrismaService } from 'src/shared/prisma-service';
import { CreateBookDto } from './dto/create-book.dto';
import { UpdateBookDto } from './dto/update-book.dto';

@Injectable()
export class BookService {
  constructor(private readonly prisma: PrismaService) {}
  async create(createBookDto: CreateBookDto) {
    if (createBookDto.readPage > createBookDto.pageCount) {
      throw new BadRequestException(
        'Gagal menambahkan buku. readPage tidak boleh lebih besar dari pageCount',
      );
    }
    return await this.prisma.book.create({
      data: {
        author: createBookDto.author ?? '',
        current_page: createBookDto.readPage ?? 0,
        finished: createBookDto.readPage === createBookDto.readPage,
        name: createBookDto.name ?? '',
        publisher: createBookDto.publisher ?? '',
        reading: createBookDto.reading ?? false,
        summary: createBookDto.summary ?? '',
        total_page: createBookDto.readPage ?? 0,
        year: createBookDto.year ?? new Date(),
      },
    });
  }

  async findAll(name?: string, isReading?: boolean, isFinished?: boolean) {
    return await this.prisma.book.findMany({
      where: {
        name: {
          contains: name,
          startsWith: name,
          endsWith: name,
        },
        reading: isReading,
        finished: isFinished,
      },
    });
  }

  async findOne(id: string): Promise<Book> {
    try {
      return await this.prisma.book.findUniqueOrThrow({ where: { id } });
    } catch (error) {
      throw new NotFoundException('Buku tidak ditemukan');
    }
  }

  async update(id: string, updateBookDto: UpdateBookDto): Promise<Book> {
    if (updateBookDto.readPage > updateBookDto.totalPage) {
      throw new BadRequestException(
        'Gagal memperbarui buku. readPage tidak boleh lebih besar dari pageCount',
      );
    }
    try {
      return await this.prisma.book.update({
        where: { id: id },
        data: {
          author: updateBookDto.author,
          current_page: updateBookDto.readPage,
          finished: updateBookDto.readPage === updateBookDto.totalPage,
          name: updateBookDto.name,
          publisher: updateBookDto.publisher,
          reading: updateBookDto.reading,
          summary: updateBookDto.summary,
          total_page: updateBookDto.totalPage,
          year: updateBookDto.year,
        },
      });
    } catch (error) {
      throw new NotFoundException('Gagal memperbarui buku. Id tidak ditemukan');
    }
  }

  async remove(id: string): Promise<Book> {
    try {
      return await this.prisma.book.delete({ where: { id } });
    } catch (error) {
      throw new NotFoundException('Buku gagal dihapus. Id tidak ditemukan');
    }
  }
}
