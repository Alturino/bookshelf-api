import {
  Body,
  Controller,
  Delete,
  Get,
  Param,
  Patch,
  Post,
  Query,
} from '@nestjs/common';
import {
  ApiBadRequestResponse,
  ApiCreatedResponse,
  ApiNotFoundResponse,
  ApiOkResponse,
  ApiOperation,
  ApiQuery,
  ApiTags,
} from '@nestjs/swagger';
import { BookService } from './book.service';
import { CreateBookDto } from './dto/create-book.dto';
import { UpdateBookDto } from './dto/update-book.dto';

@Controller('books')
@ApiTags('books')
export class BookController {
  constructor(private readonly bookService: BookService) {}

  @Post()
  @ApiOperation({ summary: 'Create book' })
  @ApiCreatedResponse({ description: 'Created book' })
  @ApiBadRequestResponse({ description: 'Book is not valid', status: 400 })
  async create(
    @Body() createBookDto: CreateBookDto,
  ): Promise<{ status: string; message: string; data: { bookId: string } }> {
    const insertedBook = await this.bookService.create(createBookDto);
    return {
      status: 'success',
      message: 'Buku berhasil ditambahkan',
      data: {
        bookId: insertedBook.id,
      },
    };
  }

  @Get()
  @ApiOperation({ summary: 'Find all book' })
  @ApiQuery({ name: 'name', required: false })
  @ApiQuery({ name: 'isReading', required: false })
  @ApiQuery({ name: 'isFinished', required: false })
  @ApiOkResponse({
    description: 'Book successfully retrieved',
    isArray: false,
  })
  async findAll(
    @Query('name') name?: string,
    @Query('isReading') isReading?: boolean,
    @Query('isFinished') isFinished?: boolean,
  ): Promise<{
    status: string;
    data: {
      books: {
        id: string;
        name: string;
        publisher: string;
      }[];
    };
  }> {
    const books = await this.bookService.findAll(name, isReading, isFinished);
    return {
      status: 'success',
      data: {
        books: books,
      },
    };
  }

  @Get(':id')
  @ApiOperation({ summary: 'Find book by id' })
  @ApiOkResponse({
    description: 'Book successfully retrieved',
    isArray: false,
  })
  @ApiNotFoundResponse({ status: 404 })
  async findOne(@Param('id') id: string): Promise<{
    author: string;
    current_page: number;
    finished: boolean;
    id: string;
    name: string;
    publisher: string;
    reading: boolean;
    summary: string;
    total_page: number;
    year: Date;
    inserted_at: Date;
    updated_at: Date;
  }> {
    return await this.bookService.findOne(id);
  }

  @Patch(':id')
  @ApiOperation({ summary: 'Update book by id' })
  @ApiOkResponse({
    description: 'Book successfully updated',
    isArray: false,
  })
  async update(@Param('id') id: string, @Body() updateBookDto: UpdateBookDto) {
    await this.bookService.update(id, updateBookDto);
    return { status: 'success', message: 'Buku berhasil diperbarui' };
  }

  @Delete(':id')
  @ApiOperation({ summary: 'Delete book by id' })
  @ApiOkResponse({
    description: 'Book successfully deleted',
    isArray: false,
  })
  async remove(@Param('id') id: string) {
    await this.bookService.remove(id);
    return { status: 'success', message: 'Buku berhasil dihapus' };
  }
}
