package ch.screenconcept.artoz.product;

/*
The extension "Artoz" is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

import java.util.Comparator;

public class ArtozProductComparator implements Comparator
{

	public int compare(Object arg0, Object arg1)
	{
		ArtozProduct product0 = (ArtozProduct) arg0;
		ArtozProduct product1 = (ArtozProduct) arg1;

		for (int pointer = 0; pointer < product0.getCode().length() && pointer < product1.getCode().length(); pointer++)
		{
			String digit0 = product0.getCode().substring(pointer, pointer + 1);
			String digit1 = product1.getCode().substring(pointer, pointer + 1);

			if (!digit0.equals("-") && digit1.equals("-"))
				return -1;

			if (digit0.equals("-") && !digit1.equals("-"))
				return 1;

			if (!digit0.equals("-") && !digit1.equals("-"))
			{
				int product0Digit = Integer.parseInt(digit0);
				int product1Digit = Integer.parseInt(digit1);
				if (product0Digit < product1Digit)
					return -1;
				if (product0Digit > product1Digit)
					return 1;
			}
		}

		if (product0.getCode().length() < product1.getCode().length())
			return -1;
		if (product0.getCode().length() > product1.getCode().length())
			return 1;

		return 0;
	}
}
