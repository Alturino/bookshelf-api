import { PartialType } from '@nestjs/mapped-types';
import { CreateBookDto } from './create-book.dto';
import { ApiProperty } from '@nestjs/swagger';
import {
  IsNumber,
  IsBoolean,
  IsDate,
  IsInt,
  IsString,
  IsNotEmpty,
} from 'class-validator';

export class UpdateBookDto extends PartialType(CreateBookDto) {
  @IsString()
  @ApiProperty({ name: 'author', example: 'Kent Beck' })
  readonly author?: string;

  @IsString()
  @ApiProperty({ name: 'name', example: 'Extreme Programming Explained' })
  @IsNotEmpty({ message: 'Gagal memperbarui buku. Mohon isi nama buku' })
  readonly name?: string;

  @IsInt({})
  @ApiProperty({ name: 'totalPage', example: 0 })
  readonly totalPage: number;

  @IsString()
  @ApiProperty({ name: 'publisher', example: "O'Reilly" })
  readonly publisher?: string;

  @IsInt({})
  @ApiProperty({ name: 'currentPage', example: 0 })
  readonly readPage: number;

  @IsBoolean()
  @ApiProperty({ name: 'reading', example: false })
  readonly reading?: boolean = false;

  @IsString()
  @ApiProperty({
    name: 'summary',
    example: `Software development projects can be fun, productive, and even daring. Yet they can consistently deliver value to a business and remain under control.

Extreme Programming (XP) was conceived and developed to address the specific needs of software development conducted by small teams in the face of vague and changing requirements. This new lightweight methodology challenges many conventional tenets, including the long-held assumption that the cost of changing a piece of software necessarily rises dramatically over the course of time. XP recognizes that projects have to work to achieve this reduction in cost and exploit the savings once they have been earned.`,
  })
  readonly summary?: string;

  @IsDate()
  @ApiProperty({
    name: 'year',
    example: new Date(),
  })
  readonly year?: Date;
}
