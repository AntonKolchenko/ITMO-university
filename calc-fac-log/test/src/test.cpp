#include "calc.h"

#include <gtest/gtest.h>

TEST(Calc, err)
{
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(0, process_line(0, "fix"));
    EXPECT_EQ("Unknown operation fix\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(11, process_line(11, "sqrt"));
    EXPECT_EQ("Unknown operation sqrt\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(17, process_line(17, "\\ 11"));
    EXPECT_EQ("Unknown operation \\ 11\n", testing::internal::GetCapturedStderr());
}

TEST(Calc, set)
{
    EXPECT_DOUBLE_EQ(0, process_line(0, "0"));
    EXPECT_DOUBLE_EQ(0, process_line(0, "0000"));
    EXPECT_DOUBLE_EQ(0, process_line(101, "0"));
    EXPECT_DOUBLE_EQ(13, process_line(0, "13"));
    EXPECT_DOUBLE_EQ(5, process_line(99, "5."));
    EXPECT_DOUBLE_EQ(0.05625, process_line(1113, "0.05625"));
    EXPECT_DOUBLE_EQ(1234567890.0, process_line(1, "1234567890"));
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(1, process_line(1, "12345678900000"));
    EXPECT_EQ("Argument isn't fully parsed, suffix left: '0000'\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(99, process_line(99, "5 "));
    EXPECT_EQ("Argument parsing error at 1: ' '\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(0, process_line(0, "0.001 11"));
    EXPECT_EQ("Argument parsing error at 5: ' 11'\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(0, process_line(0, "123-456"));
    EXPECT_EQ("Argument parsing error at 3: '-456'\n", testing::internal::GetCapturedStderr());
}

TEST(Calc, add)
{
    EXPECT_DOUBLE_EQ(7, process_line(0, "+7"));
    EXPECT_DOUBLE_EQ(7, process_line(5, "+ 2"));
    EXPECT_DOUBLE_EQ(7, process_line(5, "+ \t\t   2"));
    EXPECT_DOUBLE_EQ(2.34, process_line(1.5, "+ 0.84"));
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(9, process_line(9, "+    12345678900000"));
    EXPECT_EQ("Argument isn't fully parsed, suffix left: '0000'\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(99, process_line(99, "+ 1 "));
    EXPECT_EQ("Argument parsing error at 3: ' '\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(5, process_line(5, "+ 1 2"));
    EXPECT_EQ("Argument parsing error at 3: ' 2'\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(0, process_line(0, "+0.001 11"));
    EXPECT_EQ("Argument parsing error at 6: ' 11'\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(99, process_line(99, "+ -"));
    EXPECT_EQ("Argument parsing error at 2: '-'\nNo argument for a binary operation\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(113, process_line(113, "+"));
    EXPECT_EQ("No argument for a binary operation\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(113, process_line(113, "+) 10"));
    EXPECT_EQ("Argument parsing error at 1: ') 10'\nNo argument for a binary operation\n", testing::internal::GetCapturedStderr());
}

TEST(Calc, sub)
{
    EXPECT_DOUBLE_EQ(-11, process_line(0, "- 11"));
    EXPECT_DOUBLE_EQ(0, process_line(0, "-0"));
    EXPECT_DOUBLE_EQ(0, process_line(3, "-3"));
    EXPECT_DOUBLE_EQ(-3, process_line(7, "-10"));
    EXPECT_DOUBLE_EQ(-12344.6789, process_line(1, "- 12345.67890"));
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(77, process_line(77, "- 123a3"));
    EXPECT_EQ("Argument parsing error at 5: 'a3'\n", testing::internal::GetCapturedStderr());
}

TEST(Calc, mul)
{
    EXPECT_DOUBLE_EQ(0, process_line(0, "* 0"));
    EXPECT_DOUBLE_EQ(0, process_line(0, "*131"));
    EXPECT_DOUBLE_EQ(0, process_line(99, "* 0"));
    EXPECT_DOUBLE_EQ(8, process_line(2, "* 4"));
    EXPECT_DOUBLE_EQ(-16, process_line(-4, "*4"));
}

TEST(Calc, div)
{
    EXPECT_DOUBLE_EQ(0, process_line(0, "/ 11"));
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(11, process_line(11, "/ 0"));
    EXPECT_EQ("Bad right argument for division: 0\n", testing::internal::GetCapturedStderr());
    EXPECT_DOUBLE_EQ(3, process_line(6, "/ 2"));
    EXPECT_DOUBLE_EQ(0.7, process_line(7, "/ 10"));
    EXPECT_DOUBLE_EQ(0.3333333333333333, process_line(1, "/ 3"));
    EXPECT_DOUBLE_EQ(-0.5, process_line(-2, "/ 4"));
    EXPECT_DOUBLE_EQ(100, process_line(10, "/ 0.1"));
}

TEST(Calc, rem)
{
    EXPECT_DOUBLE_EQ(0, process_line(0, "% 3"));
    EXPECT_DOUBLE_EQ(0, process_line(4, "%4"));
    EXPECT_DOUBLE_EQ(0, process_line(-24, "%4"));
    EXPECT_DOUBLE_EQ(-3, process_line(-13, "%5"));
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(-1, process_line(-1, "%0"));
    EXPECT_EQ("Bad right argument for remainder: 0\n", testing::internal::GetCapturedStderr());
}

TEST(Calc, neg)
{
    EXPECT_DOUBLE_EQ(0, process_line(0, "_"));
    EXPECT_DOUBLE_EQ(1, process_line(-1, "_"));
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(1, process_line(1, "_ "));
    EXPECT_EQ("Unexpected suffix for a unary operation: ' '\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(1, process_line(1, "_1"));
    EXPECT_EQ("Unexpected suffix for a unary operation: '1'\n", testing::internal::GetCapturedStderr());
}

TEST(Calc, pow)
{
    EXPECT_DOUBLE_EQ(0, process_line(0, "^1"));
    EXPECT_DOUBLE_EQ(0, process_line(0, "^2"));
    EXPECT_DOUBLE_EQ(1, process_line(119, "^0"));
    EXPECT_DOUBLE_EQ(37, process_line(37, "^1"));
    EXPECT_DOUBLE_EQ(25, process_line(-5, "^2"));
    EXPECT_DOUBLE_EQ(-27, process_line(-3, "^3"));
    EXPECT_DOUBLE_EQ(5, process_line(25, "^0.5"));
}

TEST(Calc, sqrt)
{
    EXPECT_DOUBLE_EQ(1, process_line(1, "SQRT"));
    EXPECT_DOUBLE_EQ(0.7, process_line(0.49, "SQRT"));
    EXPECT_DOUBLE_EQ(5, process_line(25, "SQRT"));
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(-1, process_line(-1, "SQRT"));
    EXPECT_EQ("Bad argument for SQRT: -1\n", testing::internal::GetCapturedStderr());
}

TEST(Calc, fac)
{
    EXPECT_DOUBLE_EQ(6, process_line(3, "!"));
    EXPECT_DOUBLE_EQ(1, process_line(1, "!"));
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(-1, process_line(-1, "!"));
    EXPECT_EQ("Bad argument for factorial: -1\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(5.7, process_line(5.7, "!"));
    EXPECT_EQ("Bad argument for factorial: 5.7\n", testing::internal::GetCapturedStderr());
    EXPECT_NEAR(3.04888e+029, process_line(28, "!"), 1.0e+25);
    testing::internal::CaptureStderr();
    EXPECT_NEAR(0.2, process_line(1000 - 999.8, "!"), 1e-13);
    {
        const auto captured_stderr = testing::internal::GetCapturedStderr();
        EXPECT_FALSE(captured_stderr.empty());
        const std::string_view msg_prefix = "Bad argument for factorial:";
        EXPECT_EQ(msg_prefix, captured_stderr.substr(0, msg_prefix.size()));
    }
    testing::internal::CaptureStderr();
    EXPECT_NEAR(24, process_line((1000 - 999.8) + 3.8, "!"), 1e-13);
    EXPECT_TRUE(testing::internal::GetCapturedStderr().empty());
}

TEST(Calc, log)
{
    EXPECT_DOUBLE_EQ(3, process_line(8, "LOG 2"));
    EXPECT_DOUBLE_EQ(0.5, process_line(3, "LOG 9"));
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(-1, process_line(-1, "LOG 2"));
    EXPECT_EQ("Bad argument for logarithm: -1\n", testing::internal::GetCapturedStderr());
    testing::internal::CaptureStderr();
    EXPECT_DOUBLE_EQ(8, process_line(8, "LOG 1"));
    EXPECT_EQ("Bad base for logarithm: 1\n", testing::internal::GetCapturedStderr());
}
