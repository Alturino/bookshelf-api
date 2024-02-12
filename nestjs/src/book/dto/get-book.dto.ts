import { ApiProperty } from '@nestjs/swagger';
import { IsNotEmpty, IsNumber, IsBoolean, IsDate } from 'class-validator';
import { BookData } from '../book.interface';
import { randomUUID } from 'crypto';

export class GetBookDto {
  @ApiProperty({ name: 'id', example: randomUUID().toString() })
  readonly id: string;

  @IsNotEmpty({ message: 'Author should not be empty' })
  @ApiProperty({ name: 'author', example: 'Kent Beck' })
  readonly author: string;

  @IsNotEmpty({ message: 'Name should not be empty' })
  @ApiProperty({ name: 'name', example: 'Extreme Programming Explained' })
  readonly name: string;

  @IsNumber(
    { allowNaN: false, allowInfinity: false },
    { message: 'Total page should not be NaN and Infinity' },
  )
  @ApiProperty({ name: 'totalPage', example: 0 })
  readonly totalPage: number;

  @IsNotEmpty({ message: 'Publisher should not be empty' })
  @ApiProperty({ name: 'publisher', example: "O'Reilly" })
  readonly publisher: string;

  @IsNumber(
    { allowNaN: false, allowInfinity: false },
    { message: 'Current page should not be NaN and Infinity' },
  )
  @ApiProperty({ name: 'currentPage', example: 0 })
  readonly currentPage: number;

  @IsBoolean()
  @ApiProperty({ name: 'reading', example: false })
  readonly reading: boolean = false;

  @IsNotEmpty({ message: 'Summary should not be empty' })
  @ApiProperty({
    name: 'summary',
    example: `Software development projects can be fun, productive, and even daring. Yet they can consistently deliver value to a business and remain under control.

Extreme Programming (XP) was conceived and developed to address the specific needs of software development conducted by small teams in the face of vague and changing requirements. This new lightweight methodology challenges many conventional tenets, including the long-held assumption that the cost of changing a piece of software necessarily rises dramatically over the course of time. XP recognizes that projects have to work to achieve this reduction in cost and exploit the savings once they have been earned.`,
  })
  readonly summary: string;

  @IsDate()
  @ApiProperty({
    name: 'year',
    example: new Date(),
  })
  readonly year: Date;

  @IsBoolean()
  @ApiProperty({ name: 'finished', example: false })
  readonly finished: boolean = false;

  @IsDate()
  @ApiProperty({
    name: 'created_at',
    example: new Date(),
  })
  readonly created_at: Date;

  constructor(book: BookData) {
    this.author = book.author;
    this.created_at = book.created_at;
    this.currentPage = book.currentPage;
    this.finished = book.finished;
    this.name = book.name;
    this.id = book.id;
    this.publisher = book.publisher;
    this.reading = book.reading;
    this.summary = book.summary;
    this.totalPage = book.totalPage;
    this.year = book.year;
  }
}
